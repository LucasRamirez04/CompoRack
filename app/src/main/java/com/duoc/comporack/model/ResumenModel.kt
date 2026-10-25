package com.duoc.comporack.model

sealed class EstadoRevision(val estado: String){
    object Validado : EstadoRevision("Validado")
    object ConFalla : EstadoRevision("Con Falla")
    object Pendiente : EstadoRevision("Pendiente")
}

data class Componente(
    val id: String,
    val numero: String,
    val nombre: String,
    val categoria: String,
    val estadoRevision: EstadoRevision // Permite "Validado", "Dañado", "Pendiente"
){

}
data class RegistroAnterior (
    val estado: String = "REGISTRO COMPLETADO",
    val verificado: Boolean = true,
    val fecha: String,
    val horario: String,
    val tecnico: String,
    val rack: String,
    val componentes: List<Componente> = emptyList(),
    val totalComponente: Int = componentes.size,
){

}