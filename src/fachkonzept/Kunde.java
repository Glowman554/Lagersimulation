package fachkonzept;

import java.util.concurrent.ThreadLocalRandom;

public class Kunde
{
	// int[] kunde_anfragen = new int[] {
	// 	47,
	// 	53,
	// 	25,
	// 	27
	// };

	// int curr;

	public int frageAn()
	{
		return ThreadLocalRandom.current().nextInt(0, 99);
		// return kunde_anfragen[curr++];
	}
}
