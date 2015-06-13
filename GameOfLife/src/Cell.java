
public class Cell {
	
	static enum State {ALIVE, DEAD}
	
	private int age;
	private State state;
	private State bufferState;
	
	
	public Cell(){
		this.age = 0;
		this.state = State.DEAD;
	}
	
	int getAge(){
		return age;
	}
	
	void age(){
		if (this.state == State.ALIVE) {
			this.age++;
		}
	}
	
	void setBufferState(Cell.State newState)
	{
		this.bufferState = newState;
	}
	
	Cell.State getState()
	{
		return state;
	}
	
	void persistBufferState(){
		if (this.state == State.DEAD && this.bufferState == State.ALIVE){
			this.age = 0;
		}
		this.state = bufferState;
	}
	
}
