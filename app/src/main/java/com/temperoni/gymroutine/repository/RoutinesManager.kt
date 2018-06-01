package com.temperoni.gymroutine.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.temperoni.gymroutine.repository.dto.RoutineDto
import com.temperoni.gymroutine.repository.event.RoutinesEvent
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class RoutinesManager @Inject constructor(val bus: EventBus) {

    fun getRoutines() {
        FirebaseFirestore.getInstance().collection("routines")
                .get()
                .addOnCompleteListener {task ->
                    if (task.isSuccessful) {
                        val result = mutableListOf<RoutineDto>()
                        task.result.forEach {
                            result.add(it.toObject(RoutineDto::class.java))
                        }
                        bus.post(RoutinesEvent(result))
                    }
                }
    }
}