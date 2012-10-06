import java.util.ArrayList;


public class Utility {
	
	public Utility(){
		//behöver konstruktorn göra nått?
	}
	
	/**
	 * Hittar alla sätt som man från utgångspositionen kan flytta på lådor.
	 * @return En lista av möjliga lådförflyttningar (lådnr, ursprungsposition, ny position)
	 */
	public ArrayList<BoxPush> findPossibleMoves(Map map){
		Position playerPos = map.getMap().getPlayerPos();
	}
	
}
