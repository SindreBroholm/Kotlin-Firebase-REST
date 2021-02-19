package no.sbs.pokedex.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import no.sbs.pokedex.fragments.*

class PokemonGenAdapter(fm : FragmentManager): FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return 8
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {GenI()}
            1 -> {GenII()}
            2 -> {GenIII()}
            3 -> {GenIV()}
            4 -> {GenV()}
            5 -> {GenVI()}
            6 -> {GenVII()}
            7 -> {GenVIII()}
            else -> {GenI()}
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "I" //"Red & Blue, Yellow"
            1 -> "II"  //"Gold & Silver"
            2 -> "III"  //"Ruby & Sapphire, Emerald"
            3 -> "IV" //"Diamond & Pearl, Platinum"
            4 -> "V"  //"Black & White"
            5 -> "VI" //"X & Y"
            6 -> "VII" //"Sun & Moon"
            7 -> "VIII" //"Sword & Shield"
            else -> "I"
        }
    }
}