import com.github.ricksbrown.cowsay.Cowsay;
import com.github.ricksbrown.cowsay.plugin.CowExecutor;

/**
 * Some examples on how to use cowsay from Java.
 * @author Rick Brown
 */
public final class Moo {

	/**
	 * Private constructor.
	 */
	private Moo() {

	}

	/**
	 * Demonstrates a simple way to use cowsay from Java.
	 * @param args Some useless args.
	 */
	public static void main(final String[] args) {
		String[] cowargs = new String[]{"-f", "sheep", "Cowsay from Java using com.github.ricksbrown.cowsay.Cowsay"};
		String result = Cowsay.say(cowargs);
		System.out.println(result);

		Cowsay.main(new String[]{"-list"});

		execute();
	}

	/**
	 * Demonstrates using the cowsay plugin executor.
	 */
	public static void execute() {
		CowExecutor cowExecutor = new CowExecutor();
		cowExecutor.setCowfile("elephant");
		cowExecutor.setWrap("80");
		cowExecutor.setMessage("This is another way to execute cowsay from Java using com.github.ricksbrown.cowsay.plugin.CowExecutor");
		String result = cowExecutor.execute();
		System.out.println(result);
	}
}
