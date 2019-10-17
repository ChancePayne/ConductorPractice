package com.lambdaschool.conductordemo.controllers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.ControllerChangeType
import com.lambdaschool.conductordemo.R
import kotlin.random.Random

//class ChildController<T>(targetController: T): BaseController()
//        where T: Controller, T: ChildController.ChildContainer {
class ChildController<T>() : BaseController()
        where T: Controller, T: ChildController.ChildContainer{

    constructor(targetController: T): this() {
        setTargetController(targetController)
    }


    interface ChildContainer {
        fun receiveMessage(int: Int)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.home_controller_layout, container, false)
    }

    override fun onChangeEnded(
        changeHandler: ControllerChangeHandler,
        changeType: ControllerChangeType
    ) {
        super.onChangeEnded(changeHandler, changeType)
        val next = view?.findViewById<Button>(R.id.next_button)
        next?.setText("Pass Data")
        next?.setOnClickListener {
            // pass data to parent
            (targetController as ChildContainer).receiveMessage(Random.nextInt())
        }
        view?.findViewById<Button>(R.id.previous_button)?.setOnClickListener {
            router.popCurrentController()
        }
    }
}