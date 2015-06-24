package engine;

public class Cell {
	
	public class State{
		public final static boolean DEAD = false;
		public final static boolean ALIVE = true;
	}
	
	private int age;
	private boolean isAlive = false;
	private boolean bufferIsAlive = false;
	
	
	public Cell(){
		this.age = 0;
	}
	
	public int getAge(){
		return age;
	}
	
	public void age(){
		if (isAlive) {
			this.age++;
		}
	}
	
	public void setBufferState(boolean newState)
	{
		this.bufferIsAlive = newState;
	}
	
	public boolean isAlive()
	{
		return isAlive;
	}
	
	public void persistBufferState(){
		if (!isAlive && bufferIsAlive){
			this.age = 0;
		}
		this.isAlive = bufferIsAlive;
	}

	public void toggle() {
		this.bufferIsAlive = !isAlive;
	}
	
}
