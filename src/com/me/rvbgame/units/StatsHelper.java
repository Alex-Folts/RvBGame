package com.me.rvbgame.units;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.me.rvbgame.RvBUnit;

public class StatsHelper {

    public static final int MAX_VALUE = 300;
    public static final int AVERAGE_VALUE = 150;
    public static final int MIN_VALUE = 50;
    public static final int ZERO_VALUE = 0;
    public static JSONObject js;
    public static void initiate(RvBUnit unit){
        String filePath = "";
        switch (unit.unitType) {
            case UNIT_TYPE_DEFENDER:
                filePath = "D:\\Dropbox\\0GeekRep\\RvBGame-android\\assets\\data\\json_files\\defender.json";
                break;
            case UNIT_TYPE_MELEE:
                filePath = "D:\\Dropbox\\0GeekRep\\RvBGame-android\\assets\\data\\json_files\\melee.json";
                break;
            case UNIT_TYPE_SPECIAL:
                filePath = "D:\\Dropbox\\0GeekRep\\RvBGame-android\\assets\\data\\json_files\\special_stan.json";
                break;
            case UNIT_TYPE_RANGED:
                filePath = "D:\\Dropbox\\0GeekRep\\RvBGame-android\\assets\\data\\json_files\\ranged.json";
                break;
            case UNIT_TYPE_RANGED_MASS:
                filePath = "D:\\Dropbox\\0GeekRep\\RvBGame-android\\assets\\data\\json_files\\ranged_mass.json";
                break;
            case UNIT_TYPE_TOWER:
                filePath = "D:\\Dropbox\\0GeekRep\\RvBGame-android\\assets\\data\\json_files\\tower.json";
                break;
        }

        JSONObject jsonObject = StatsHelper.parsedJSON(filePath);

        Long currValue = (Long) jsonObject.get("health");
        unit.setHealth(currValue.intValue());

        currValue = (Long) jsonObject.get("energy");
        unit.setEnergy(currValue.intValue());

        currValue = (Long) jsonObject.get("physical_attack");
        unit.setpAttack(currValue.intValue());

        currValue = (Long) jsonObject.get("physical_defence");
        unit.setpDefence(currValue.intValue());

        currValue = (Long) jsonObject.get("intelligence_attack");
        unit.setiAttack(currValue.intValue());

        currValue = (Long) jsonObject.get("intelligence_defence");
        unit.setiDefence(currValue.intValue());

        currValue = (Long) jsonObject.get("targets_num");
        unit.setTargetsNum(currValue.byteValue());

        currValue = (Long) jsonObject.get("targets_num");
        unit.setAttackRange(currValue.byteValue());

        currValue = null;

    }



    public static JSONObject parsedJSON(String filePath){
        JSONObject jsonObject = null;        
        try {
            // read the json file

            FileReader reader = null;
            try {
                reader = new FileReader(filePath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            JSONParser jsonParser = new JSONParser();

            try {
                jsonObject = (JSONObject) jsonParser.parse(reader);
                js = jsonObject;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return jsonObject;
    }

//    public static void main(String[] args) {
//
//        UnitDefender unitDefender = new UnitDefender();
//        System.out.println(StatsHelper.js.toJSONString());
//        UnitMelee unitMelee = new UnitMelee();
//        System.out.println(StatsHelper.js.toJSONString());
//        UnitRanged unitRanged = new UnitRanged();
//        System.out.println(StatsHelper.js.toJSONString());
//        UnitSpecialStan unitSpecialStan = new UnitSpecialStan();
//        System.out.println(StatsHelper.js.toJSONString());
//        UnitRangedMass unitRangedMass = new UnitRangedMass();
//        System.out.println(StatsHelper.js.toJSONString());
//
//    }

}
