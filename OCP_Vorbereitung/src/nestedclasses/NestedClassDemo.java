package nestedclasses;

class TopLevel{
	int topInt;
	static int staticTopInt;
	final int finalTopInt=5;
	
	class Member{
		int memberInt;
		//static int staticMemberInt;  //so wird das nichts: Klasse ist nicht static darum keine statischen nicht final Variablen
		final int finalMemberInt=51;
		void memberMethode(){	
			topInt=2; //kein Problem
			staticTopInt=4; //kein Problem
			System.out.println(finalTopInt); //kein Problem
		}
	}
	
}
public class NestedClassDemo {

	public static void main(String[] args) {
		

	}

}
