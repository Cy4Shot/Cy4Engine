package core.kernel;

public class Game {
	
	private int samples = 5;
	
	protected CoreEngine engine;
	
	public Game(){
		engine = new CoreEngine();
	}
	
	public void launch(){
		engine.start();
	}
	
	public void init(){
		engine.init();
	}
	
	public CoreEngine getEngine() {
		return engine;
	}
	public void setEngine(CoreEngine engine) {
		this.engine = engine;
	}

	public int getSamples() {
		return samples;
	}

	public void setSamples(int samples) {
		this.samples = samples;
	}
}
