package com.uvg.profitpulse.utils

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.uvg.profitpulse.Model.Gastos
import com.uvg.profitpulse.Model.Recordatorios
import com.uvg.profitpulse.Model.Ventas
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RealtimeManager(context: android.content.Context) {
    private val databaseReferenceG: DatabaseReference = FirebaseDatabase.getInstance().reference.child("gastos")
    private val databaseReferenceR: DatabaseReference = FirebaseDatabase.getInstance().reference.child("recordatorios")
    private val databaseReferenceV: DatabaseReference = FirebaseDatabase.getInstance().reference.child("ventas")
    private val authManager = AuthManager()

    fun addGasto(gastos: Gastos){
        val key = databaseReferenceG.push().key
        if (key != null){
            databaseReferenceG.child(key).setValue(gastos)
        }
    }
    fun deleteGasto(gastoId: String){
        databaseReferenceG.child(gastoId).removeValue()
    }

    fun getGasto(): Flow<List<Gastos>> {
        val idFilter = authManager.getCurrentUser()?.uid

        val flow = callbackFlow {
            val listener = databaseReferenceG.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val contacts = snapshot.children.mapNotNull { snapshot ->
                        val contact = snapshot.getValue(Gastos::class.java)
                        snapshot.key?.let { contact?.copy(key = it) }
                    }
                    trySend(contacts.filter { it.uid == idFilter }).isSuccess
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            })
            awaitClose { databaseReferenceG.removeEventListener(listener) }
        }
        return flow

    }
    fun addRemidner(recordatorios: Recordatorios){
        val key = databaseReferenceR.push().key
        if (key != null){
            databaseReferenceR.child(key).setValue(recordatorios)
        }
    }
    fun deleteReminder(recordatoriosId: String){
        databaseReferenceR.child(recordatoriosId).removeValue()
    }

    fun getReminder(): Flow<List<Recordatorios>> {
        val idFilter = authManager.getCurrentUser()?.uid

        val flow = callbackFlow {
            val listener = databaseReferenceR.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val contacts = snapshot.children.mapNotNull { snapshot ->
                        val contact = snapshot.getValue(Recordatorios::class.java)
                        snapshot.key?.let { contact?.copy(key = it) }
                    }
                    trySend(contacts.filter { it.uid == idFilter }).isSuccess
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            })
            awaitClose { databaseReferenceR.removeEventListener(listener) }
        }
        return flow

    }
    fun addVenta(ventas: Ventas){
        val key = databaseReferenceV.push().key
        if (key != null){
            databaseReferenceV.child(key).setValue(ventas)
        }
    }
    fun deleteVenta(ventaId: String){
        databaseReferenceV.child(ventaId).removeValue()
    }

    fun getVentas(): Flow<List<Ventas>> {
        val idFilter = authManager.getCurrentUser()?.uid

        val flow = callbackFlow {
            val listener = databaseReferenceV.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val contacts = snapshot.children.mapNotNull { snapshot ->
                        val contact = snapshot.getValue(Ventas::class.java)
                        snapshot.key?.let { contact?.copy(key = it) }
                    }
                    trySend(contacts.filter { it.uid == idFilter }).isSuccess
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            })
            awaitClose { databaseReferenceV.removeEventListener(listener) }
        }
        return flow

    }

}