public class Matrix
{
	public double[][] connections;
	public Matrix(int prevSize, int nextSize)
	{
		this.connections = new double[prevSize][nextSize];
	}

	public Matrix(double[] values)
	{
		this.connections = new double[1][values.length];
		for (int i = 0; i < values.length; i++)
		{
			this.connections[0][i] = values[i];
		}
	}

	public Matrix transpose()
	{
		Matrix t = new Matrix(this.connections[0].length, this.connections.length);
		for (int i = 0; i < connections.length; i++)
		{
			for (int j = 0; j < connections[0].length; j++)
			{
				t.connections[j][i] = this.connections[i][j];
			}
		}

		return t;
	}

	public Matrix multiply(Matrix right)
	{
		if (this.connections[0].length != right.connections.length)
		{
			throw new IllegalArgumentException();
		}

		Matrix result = new Matrix(this.connections.length, right.connections[0].length);
		for (int leftRow = 0; leftRow < this.connections.length; leftRow++)
		{
			for (int rightColumn = 0; rightColumn < right.connections[0].length; rightColumn++)
			{
				double total = 0;
				for (int secondaryIndex = 0; secondaryIndex < this.connections[0].length; secondaryIndex++)
				{
					total += this.connections[leftRow][secondaryIndex] * right.connections[secondaryIndex][rightColumn];
				}

				result.connections[leftRow][rightColumn] = total;
			}
		}

		return result;
	}

	public Matrix minus(Matrix other)
	{
		if (this.connections.length != other.connections.length ||
			this.connections[0].length != other.connections[0].length)
		{
			throw new IllegalArgumentException();
		}

		Matrix result = new Matrix(this.connections.length, this.connections[0].length);
		for (int row = 0; row < this.connections.length; row++)
		{
			for (int col = 0; col < this.connections[0].length; col++)
			{
				result.connections[row][col] = this.connections[row][col] - other.connections[row][col];
			}
		}

		return result;
	}

	public Matrix sigmoid()
	{
		Matrix result = new Matrix(this.connections.length, this.connections[0].length);
		for (int i = 0; i < this.connections.length; i++)
		{
			for (int j = 0; j < this.connections[0].length; j++)
			{
				result.connections[i][j] = sigmoidValue(this.connections[i][j]);
			}
		}

		return result;
	}

	public int elementCount()
	{
		return this.connections.length * this.connections[0].length;
	}

	private static double sigmoidValue(double original)
	{
		return 1.0 / (1 + Math.pow(Math.E, -original));
	}
}