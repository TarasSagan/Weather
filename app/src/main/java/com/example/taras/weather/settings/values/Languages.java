package com.example.taras.weather.settings.values;


import com.example.taras.weather.settings.values.models.Lang;

import java.util.ArrayList;
import java.util.List;

public class Languages {
    private Lang Arabic;
    private Lang Bulgarian;
    private Lang Catalan;
    private Lang Czech;
    private Lang German;
    private Lang Greek;
    private Lang English;
    private Lang Persian;
    private Lang Finnish;
    private Lang French;
    private Lang Galician;
    private Lang Croatian;
    private Lang Hungarian;
    private Lang Italian;
    private Lang Japanese;
    private Lang Korean;
    private Lang Latvian;
    private Lang Lithuanian;
    private Lang Macedonian;
    private Lang Dutch;
    private Lang Polish;
    private Lang Portuguese;
    private Lang Romanian;
    private Lang Russian;
    private Lang Swedish;
    private Lang Slovak;
    private Lang Slovenian;
    private Lang Spanish;
    private Lang Turkish;
    private Lang Ukrainian;
    private Lang Vietnamese;
    private Lang ChineseSimplified;
    private Lang ChineseTraditional;

    public Languages() {
        initLangs();
    }
    public List<Lang> getListLanguages(){
        List<Lang> list = new ArrayList<>();
        list.add(Arabic);
        list.add(Bulgarian);
        list.add(Catalan);
        list.add(German);
        list.add(Greek);
        list.add(English);
        list.add(Persian);
        list.add(Finnish);
        list.add(French);
        list.add(Galician);
        list.add(Croatian);
        list.add(Hungarian);
        list.add(Italian);
        list.add(Japanese);
        list.add(Korean);
        list.add(Latvian);
        list.add(Lithuanian);
        list.add(Macedonian);
        list.add(Dutch);
        list.add(Polish);
        list.add(Portuguese);
        list.add(Romanian);
        list.add(Russian);
        list.add(Swedish);
        list.add(Slovak);
        list.add(Slovenian);
        list.add(Spanish);
        list.add(Turkish);
        list.add(Turkish);
        list.add(Ukrainian);
        list.add(Vietnamese);
        list.add(ChineseSimplified);
        list.add(ChineseTraditional);
      return list;
    }

    private void initLangs() {
        Arabic = new Lang("Arabic", "ar");
        Bulgarian = new Lang("Bulgarian", "bg");
        Catalan = new Lang("Catalan", "ca");
        Czech = new Lang("Czech", "cz");
        German = new Lang("German", "de");
        Greek = new Lang("Greek", "el");
        English = new Lang("English", "en");
        Persian = new Lang("Persian", "fa");
        Finnish = new Lang("Finnish", "fi");
        French = new Lang("French", "fr");
        Galician = new Lang("Galician", "gl");
        Croatian = new Lang("Croatian", "hr");
        Hungarian = new Lang("Hungarian", "hu");
        Italian = new Lang("Italian", "it");
        Japanese = new Lang("Japanese", "ja");
        Korean = new Lang("Korean", "kr");
        Latvian = new Lang("Latvian", "la");
        Lithuanian = new Lang("Lithuanian", "lt");
        Macedonian = new Lang("Macedonian", "mk");
        Dutch = new Lang("Dutch", "nl");
        Polish = new Lang("Polish", "pl");
        Portuguese = new Lang("Portuguese", "pt");
        Romanian = new Lang("Romanian", "ro");
        Russian = new Lang("Russian", "ru");
        Swedish = new Lang("Swedish", "se");
        Slovak = new Lang("Slovak", "sk");
        Slovenian = new Lang("Slovenian", "sl");
        Spanish = new Lang("Spanish", "es");
        Turkish = new Lang("Turkish", "tr");
        Ukrainian = new Lang("Ukrainian", "ua");
        Vietnamese = new Lang("Vietnamese", "vi");
        ChineseSimplified = new Lang("Chinese Simplified", "zh_cn");
        ChineseTraditional = new Lang("Chinese Traditional", "zh_tw");
    }

    public Lang getArabic() {
        return Arabic;
    }

    public Lang getBulgarian() {
        return Bulgarian;
    }

    public Lang getCatalan() {
        return Catalan;
    }

    public Lang getCzech() {
        return Czech;
    }

    public Lang getGerman() {
        return German;
    }

    public Lang getGreek() {
        return Greek;
    }

    public Lang getEnglish() {
        return English;
    }

    public Lang getPersian() {
        return Persian;
    }

    public Lang getFinnish() {
        return Finnish;
    }

    public Lang getFrench() {
        return French;
    }

    public Lang getGalician() {
        return Galician;
    }

    public Lang getCroatian() {
        return Croatian;
    }

    public Lang getHungarian() {
        return Hungarian;
    }

    public Lang getItalian() {
        return Italian;
    }

    public Lang getJapanese() {
        return Japanese;
    }

    public Lang getKorean() {
        return Korean;
    }

    public Lang getLatvian() {
        return Latvian;
    }

    public Lang getLithuanian() {
        return Lithuanian;
    }

    public Lang getMacedonian() {
        return Macedonian;
    }

    public Lang getDutch() {
        return Dutch;
    }

    public Lang getPolish() {
        return Polish;
    }

    public Lang getPortuguese() {
        return Portuguese;
    }

    public Lang getRomanian() {
        return Romanian;
    }

    public Lang getRussian() {
        return Russian;
    }

    public Lang getSwedish() {
        return Swedish;
    }

    public Lang getSlovak() {
        return Slovak;
    }

    public Lang getSlovenian() {
        return Slovenian;
    }

    public Lang getSpanish() {
        return Spanish;
    }

    public Lang getTurkish() {
        return Turkish;
    }

    public Lang getUkrainian() {
        return Ukrainian;
    }

    public Lang getVietnamese() {
        return Vietnamese;
    }

    public Lang getChineseSimplified() {
        return ChineseSimplified;
    }

    public Lang getChineseTraditional() {
        return ChineseTraditional;
    }
}
