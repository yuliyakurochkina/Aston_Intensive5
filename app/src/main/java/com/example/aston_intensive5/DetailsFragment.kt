package com.example.aston_intensive5

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private var editName: EditText? = null
    private var editLastName: EditText? = null
    private var editNumber: EditText? = null
    private var btnConfirm: Button? = null

    private var contactPosition = 0

    companion object {
        private const val ARGS_NAME = "ARGS_NAME"
        private const val ARGS_LASTNAME = "ARGS_LNAME"
        private const val ARGS_NUMBER = "ARGS_NUMBER"
        private const val ARGS_POSITION = "ARGS_POS"

        fun newDetailFragment(contactData: Contact, pos: Int): DetailsFragment {
            val detailFragment = DetailsFragment()
            val args = createBundleWithContactData(contactData)
            putPosInBundle(args, pos)
            detailFragment.arguments = args

            return detailFragment
        }

        private fun createBundleWithContactData(contactData: Contact): Bundle {
            val args = Bundle()
            args.putString(ARGS_NAME, contactData.name)
            args.putString(ARGS_LASTNAME, contactData.lastName)
            args.putString(ARGS_NUMBER, contactData.number)
            return args
        }

        private fun putPosInBundle(bundle: Bundle, pos: Int): Bundle {
            bundle.putInt(ARGS_POSITION, pos)
            return bundle
        }

        fun getPosition(bundle: Bundle): Int {
            return bundle.getInt(ARGS_POSITION)
        }

        fun getContactDataFromBundle(bundle: Bundle): Contact {
            val contactData = Contact("", "", "")
            bundle.getString(ARGS_NAME)?.let {
                contactData.name = it
            }
            bundle.getString(ARGS_LASTNAME)?.let {
                contactData.lastName = it
            }
            bundle.getString(ARGS_NUMBER)?.let {
                contactData.number = it
            }
            return contactData
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        val bundle = arguments
        bundle?.let {
            setInfo(bundle)

            btnConfirm?.setOnClickListener {
                val contactData = Contact(
                    "", "", ""
                )
                editName?.text?.let {
                    contactData.name = it.toString()
                }
                editLastName?.text?.let {
                    contactData.lastName = it.toString()
                }
                editNumber?.text?.let {
                    contactData.number = it.toString()
                }
                val bundle1 = createBundleWithContactData(contactData)
                putPosInBundle(bundle1, contactPosition)

                setFragmentResult(ListOfContactsFragment.REQUEST_KEY, bundle1)
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    private fun initViews() {
        editName = view?.findViewById(R.id.edit_name)
        editLastName = view?.findViewById(R.id.edit_lastname)
        editNumber = view?.findViewById(R.id.edit_number)
        btnConfirm = view?.findViewById(R.id.btn_confirm)
    }

    private fun setInfo(bundle: Bundle) {
        (bundle.getString(ARGS_NAME))?.let {
            editName?.text = Editable.Factory.getInstance().newEditable(it)
        }
        (bundle.getString(ARGS_LASTNAME))?.let {
            editLastName?.text = Editable.Factory.getInstance().newEditable(it)
        }
        (bundle.getString(ARGS_NUMBER))?.let {
            editNumber?.text = Editable.Factory.getInstance().newEditable(it)
        }
        (bundle.getInt(ARGS_POSITION)).let {
            contactPosition = it
        }
    }
}