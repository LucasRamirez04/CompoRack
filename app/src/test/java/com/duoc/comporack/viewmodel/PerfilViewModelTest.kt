package com.duoc.comporack.viewmodel

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PerfilViewModelTest {

    private lateinit var viewModel: PerfilViewModel

    // Antes de cada prueba se crea un ViewModel nuevo para que no compartan estado
    @Before
    fun preparar() {
        viewModel = PerfilViewModel()
    }

    @Test
    fun `una peticion vacia muestra error y no se envia`() {
        // Se abre el modal y se deja el campo vacío
        viewModel.abrirModal()
        assertTrue(viewModel.mostrarModal)
        viewModel.actualizarPeticion("")

        // Envia peticion
        viewModel.enviarPeticion()

        // Comprueba que hay error, no se envio y el modal sigue abierto
        assertTrue(viewModel.errorPeticion.tieneError)
        assertEquals("La petición no puede estar vacía", viewModel.errorPeticion.mensaje)
        assertFalse(viewModel.peticionEnviada)
        assertTrue(viewModel.mostrarModal)
    }

    @Test
    fun `una peticion muy corta muestra error de largo minimo`() {
        viewModel.abrirModal()
        viewModel.actualizarPeticion("hola")

        viewModel.enviarPeticion()

        assertTrue(viewModel.errorPeticion.tieneError)
        assertEquals("Describe el cambio con al menos 10 caracteres", viewModel.errorPeticion.mensaje)
        assertFalse(viewModel.peticionEnviada)
    }

    @Test
    fun `una peticion valida se envia y cierra el modal`() {
        viewModel.abrirModal()
        viewModel.actualizarPeticion("Cambiar mi rol a supervisor")

        viewModel.enviarPeticion()

        assertTrue(viewModel.peticionEnviada)
        assertFalse(viewModel.mostrarModal)
        assertFalse(viewModel.errorPeticion.tieneError)
    }
}