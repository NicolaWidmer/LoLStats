package lolstats;

import com.github.kittinunf.fuel.*;
import com.github.kittinunf.fuel.json.*;
import org.json.JSONObject;

var api_key:String = "";

var region:String = "euw1";
var greater_region:String = "europe"

fun main(){
    println(getMatchHistory(getPuuid("xXLegoIas"), 15));
}

fun getPuuid(summonerName:String):String{
    val (_,_,res) = ("https://"+region+".api.riotgames.com/lol/summoner/v4/summoners/by-name/"+summonerName)
                        .httpGet(listOf("api_key" to api_key))
                        .responseJson();
    val user_json = res.component1()!!.obj();
    return user_json["puuid"] as String;
}

fun getMatchHistory(puuid:String,count:Int):List<String>?{
    if(count<=0||count>100)
        return null;
    val (req,_,res) = ("https://"+greater_region+".api.riotgames.com/lol/match/v5/matches/by-puuid/"+puuid+"/ids")
                    .httpGet(listOf("api_key" to api_key, 
                                    "count" to count))
                    .responseJson();
    println(req);
    println(res);
    val history_json = res.component1()!!.array();
    var ans:MutableList<String> = mutableListOf<String>()  ;
    for(id in history_json){
        ans.add(id as String);
    }
    return ans;

}