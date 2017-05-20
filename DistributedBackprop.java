public class DistributedBackprop
{
	public static class BatchLoader

	extends Mapper<Network, Text, Network, DataSample>
	{
		public void map(Network key, Text value, Context context) throws IOException, InterruptedException 
		{
			StringTokenizer itr = new StringTokenizer(value.toString(), ",");
			while (itr.hasMoreTokens()) 
			{
				DataSample nextSample = new DataSample();
				nextSample.inputs = new double[2];
				nextSample.outputs = new double[1];
				nextSample.inputs[0] = Integer.parseInt(itr.nextToken());
				nextSample.inputs[1] = Integer.parseInt(itr.nextToken());
				nextSample.outputs[0] = Integer.parseInt(itr.nextToken());

				context.write(key, nextSample);
			}
		}
	}

	public static void main(String[] args)
	{
		
	}

	private static void runLocally()
	{
		double[][] inputs = {	{0,0},
								{0,1},
								{1,0},
								{1,1}};

		double[][] outputs = {	{0},
								{1},
								{1},
								{0}};

		Network xorNetwork = new Network(2, 2, 1);

		for (int epoch = 0; epoch < 100001; epoch++)
		{
			for (int i = 0; i < 4; i++)
			{
				WeightDelta[] deltas = xorNetwork.backprop(inputs[i], outputs[i]);

				for (WeightDelta delta : deltas)
				{
					xorNetwork.applyDelta(delta);
				}
			}

			if (epoch % 500 == 0)
			{
				printAverageError(inputs, outputs, xorNetwork);
			}
		}
	}

	private static void printAverageError(double[][] inputs, double[][] outputs, Network network)
	{
		double error = 0;
		for (int i = 0; i < inputs.length; i++)
		{
			Matrix actualOutput = network.calculate(inputs[i]);
			error += new Matrix(outputs[i]).minus(actualOutput).abs().sum();
		}

		System.out.println("Average error: " + error / inputs.length);
	}
}