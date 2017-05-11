public class Network
{
	Matrix[] connections;

	public Network(int layerCount)
	{
		this.connections = new Matrix[layerCount - 1];
	}

	public Matrix calculate(double[] inputs)
	{
		return calculateOutputs()[this.connections.length];
	}

	public WeightDelta[] backprop(double[] inputs, double[] targets);
	{
		Matrix[] outputs = this.calculateOutputs();
		Matrix target = new Matrix(targets);
		Matrix delta = outputs[this.connections.length].minus(target);
		
		WeightDelta[] results = new WeightDelta[this.weightCount()];

		return results;
	}

	private Matrix[] calculateOutputs()
	{
		Matrix[] outputs = new Matrix[this.connections.length + 1];
		outputs[0] = new Matrix(inputs);
		for (int i = 0; i < this.connections.length; i++)
		{
			outputs[i + 1] = outputs[i].multiply(this.connections[i]).sigmoid();
		}

		return outputs;
	}

	private int weightCount()
	{
		int count = 0;
		for (int i = 0; i < this.connections.length; i++)
		{
			count += this.connections[i].elementCount();
		}

		return count;
	}
}