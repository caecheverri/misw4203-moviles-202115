package com.sinapsis.vinilos

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.sinapsis.vinilos.views.MainActivity
import com.sinapsis.vinilos.views.adapters.ArtistaAdapter
import com.sinapsis.vinilos.views.adapters.AlbumAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class VinilosTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_navegacionAOpcionesRoles() {
        onView(withId(R.id.btIngresar)).perform(click())
        onView(withId(R.id.btColeccionista)).check(matches(isDisplayed()))
        onView(withId(R.id.btUsuario)).check(matches(isDisplayed()))
    }

    @Test
    fun test_navegacionAtrasDesdeOpcionesRoles() {
        onView(withId(R.id.btIngresar)).perform(click())
        pressBack()
        onView(withId(R.id.btIngresar)).check(matches(isDisplayed()))
    }

    @Test
    fun test_listaFragmentoArtistaVisible() {
        onView(withId(R.id.btIngresar)).perform(click())
        onView(withId(R.id.btColeccionista)).perform(click())
        onView(withId(R.id.ic_artist)).perform(click())

        onView(withId(R.id.rvArtista)).check(matches(isDisplayed()))
    }

    @Test
    fun test_listaFragmentoAlbumesVisible() {
        onView(withId(R.id.btIngresar)).perform(click())
        onView(withId(R.id.btColeccionista)).perform(click())
        onView(withId(R.id.ic_disc)).perform(click())

        onView(withId(R.id.rvAlbum)).check(matches(isDisplayed()))
    }

    @Test
    fun test_listaFragmentoDetalleAlbumesVisible() {
        onView(withId(R.id.btIngresar)).perform(click())
        onView(withId(R.id.btColeccionista)).perform(click())
        onView(withId(R.id.ic_disc)).perform(click())

        onView(withId(R.id.rvAlbum)).check(matches(isDisplayed()))

    }

    @Test
    fun test_listaFragmentoColeccionistasVisibles()
    {
        onView(withId(R.id.btIngresar)).perform(click())

        onView(withId(R.id.btColeccionista)).perform(click())
        onView(withId(R.id.ic_collector)).perform(click())

        onView(withId(R.id.rvColeccionista)).check(matches(isDisplayed()))

    }

    @Test
    fun test_detalleArtistaPosicionDosVisible() {
        onView(withId(R.id.btIngresar)).perform(click())
        onView(withId(R.id.btColeccionista)).perform(click())
        onView(withId(R.id.ic_artist)).perform(click())

        onView(withId(R.id.rvArtista)).check(matches(isDisplayed()))

        onView(withId(R.id.rvArtista)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ArtistaAdapter.ArtistaViewHolder>(2, click()))

        onView(withId(R.id.viewDetalleArtista)).check(matches(isDisplayed()))
    }

    @Test
    fun test_detalleArtistaPosicionCuatroVisible() {
        onView(withId(R.id.btIngresar)).perform(click())
        onView(withId(R.id.btColeccionista)).perform(click())
        onView(withId(R.id.ic_artist)).perform(click())

        onView(withId(R.id.rvArtista)).check(matches(isDisplayed()))

        onView(withId(R.id.rvArtista)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ArtistaAdapter.ArtistaViewHolder>(4, click()))

        onView(withId(R.id.viewDetalleArtista)).check(matches(isDisplayed()))
    }

    @Test
    fun test_detalleColeccionistaFavoritosVisible() {
        onView(withId(R.id.btIngresar)).perform(click())
        onView(withId(R.id.btColeccionista)).perform(click())
        onView(withId(R.id.ic_collector)).perform(click())

        onView(withId(R.id.rvColeccionista)).check(matches(isDisplayed()))

        onView(withId(R.id.viewDetalleColeccionista)).check(matches(isDisplayed()))
    }
}