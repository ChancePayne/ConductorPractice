package com.lambdaschool.conductordemo.controllers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.ControllerChangeType
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler
import com.lambdaschool.conductordemo.R

class HomeController(private var message: String? = null) : BaseController(), ChildController.ChildContainer {
    override fun receiveMessage(int: Int) {
        message = message?.plus(": $int")
        view?.findViewById<TextView>(R.id.home_textview)?.text = message?:"Hello Conductor!\nController ${router.backstackSize}"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.home_controller_layout, container, false)
        view.findViewById<TextView>(R.id.home_textview).text = message?:"Hello Conductor!\nController ${router.backstackSize}"
        return view
    }

    fun getMessage(string: String) {
        // handle message
    }

    override fun onChangeEnded(
        changeHandler: ControllerChangeHandler,
        changeType: ControllerChangeType
    ) {
        super.onChangeEnded(changeHandler, changeType)

        view?.findViewById<Button>(R.id.next_button)?.setOnClickListener {
            router.pushController(
                RouterTransaction.with(ChildController(this))
                    .pushChangeHandler(HorizontalChangeHandler())
                    .popChangeHandler(HorizontalChangeHandler())
            )
        }

        val prevButton = view?.findViewById<Button>(R.id.previous_button)
        if(router.backstackSize <= 1) {
            prevButton?.visibility = View.GONE
        } else {
//            (parentController as HomeController).getMessage("new message")

            prevButton?.setOnClickListener {
                router.popCurrentController()
            }
        }
    }
}