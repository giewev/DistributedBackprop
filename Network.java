public class Network
{
	Matrix[] connections;
	private double learningRate = -0.5;

	public Network(int... nodeCounts)
	{
		this.connections = new Matrix[nodeCounts.length - 1];
		for (int i = 0; i < nodeCounts.length - 1; i++)
		{
			this.connections[i] = new Matrix(nodeCounts[i] + 1, nodeCounts[i+1]);
			this.connections[i].randomize();
		}
	}

	public Matrix calculate(double[] inputs)
	{
		return calculateOutputs(inputs)[this.connections.length];
	}

	public WeightDelta[] backprop(double[] inputs, double[] targets)
	{
		Matrix[] outputs = this.calculateOutputs(inputs);
		Matrix target = new Matrix(targets);
		Matrix[] deltas = new Matrix[this.connections.length];
		Matrix derivative = outputs[this.connections.length].minus(target);
		for (int i = this.connections.length - 1; i >= 0; i--)
		{
			if (i != this.connections.length - 1)
			{
				derivative = derivative.multiply(this.connections[i + 1].transpose().withoutBiasColumn());
			}

			derivative = derivative.flatMultiply(outputs[i + 1].sigmoidDerivative());
			deltas[i] = outputs[i].transpose().multiply(derivative);
		}

		WeightDelta[] results = flattenDeltas(deltas);

		return results;
	}

	public void applyDelta(WeightDelta delta)
	{
		WeightIndex index = delta.index;
		this.connections[index.layer].values[index.x][index.y] += this.learningRate * delta.delta;
	}

	private WeightDelta[] flattenDeltas(Matrix[] deltas)
	{
		WeightDelta[] results = new WeightDelta[this.weightCount()];
		int resultIndex = 0;
		for (int layer = 0; layer < deltas.length; layer++)
		{
			for (int x = 0; x < deltas[layer].values.length; x++)
			{
				for (int y = 0; y < deltas[layer].values[0].length; y++)
				{
					results[resultIndex] = new WeightDelta(new WeightIndex(layer, x, y), deltas[layer].values[x][y]);
					resultIndex++;
				}
			}
		}

		return results;
	}

	private Matrix[] calculateOutputs(double[] inputs)
	{
		Matrix[] outputs = new Matrix[this.connections.length + 1];
		outputs[0] = new Matrix(inputs).withBiasColumn();
		for (int i = 0; i < this.connections.length; i++)
		{
			outputs[i + 1] = outputs[i].multiply(this.connections[i]).sigmoid();

			if (i != this.connections.length - 1)
			{
				outputs[i + 1] = outputs[i + 1].withBiasColumn();
			}
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