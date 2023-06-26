package com.example.imagescale

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.imagescale.databinding.FragmentLocalImageBinding
import kotlin.random.Random


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RandomImageFragment : Fragment() {

    private var _binding: FragmentLocalImageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLocalImageBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(binding.close) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemGestures())
            // Apply the insets as padding to the view. Here we're setting all of the
            // dimensions, but apply as appropriate to your layout. You could also
            // update the views margin if more appropriate.
            view.updatePadding(insets.left, insets.top, insets.right, insets.bottom)

            // Return CONSUMED if we don't want the window insets to keep being passed
            // down to descendant views.
            WindowInsetsCompat.CONSUMED
        }

        pickOutImage()

        binding.close.setOnClickListener {
            findNavController().navigate(R.id.action_RandomImageFragment_to_MainFragment)
        }
// Create an ArrayAdapter using the string array and a default spinner layout
        setUpScales()



    }

    private fun pickOutImage() {

        var nextInt = Random.nextInt(0, 4)

        var resource = when (nextInt) {

            0 -> R.drawable.image
            1 -> R.drawable.placeholder
            2 -> R.drawable.error
            3 -> R.drawable.imagem2
            else -> R.drawable.imagewebp
        }

        binding.image.setImageResource(resource)

    }

    private fun setUpScales() {

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.scale_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spnScales.adapter = adapter
        }

        binding.spnScales.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {


            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {


                val scales = requireContext().resources.getStringArray(R.array.scale_types)

                scales.getOrNull(position)?.let {

                    binding.image.scaleType = ImageView.ScaleType.valueOf(it)
                }


            }

        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}