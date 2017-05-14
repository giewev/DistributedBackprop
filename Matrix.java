public class Matrix
{
	public double[][] values;
	public Matrix(int prevSize, int nextSize)
	{
		this.values = new double[prevSize][nextSize];
	}

	public Matrix(double[] values)
	{
		this.values = new double[1][values.length];
		for (int i = 0; i < values.length; i++)
		{
			this.values[0][i] = values[i];
		}
	}

	public Matrix transpose()
	{
		Matrix t = new Matrix(this.values[0].length, this.values.length);
		for (int i = 0; i < values.length; i++)
		{
			for (int j = 0; j < values[0].length; j++)
			{
				t.values[j][i] = this.values[i][j];
			}
		}

		return t;
	}

	public Matrix multiply(Matrix right)
	{
		if (this.values[0].length != right.values.length)
		{
			throw new IllegalArgumentException();
		}

		Matrix result = new Matrix(this.values.length, right.values[0].length);
		for (int leftRow = 0; leftRow < this.values.length; leftRow++)
		{
			for (int rightColumn = 0; rightColumn < right.values[0].length; rightColumn++)
			{
				double total = 0;
				for (int secondaryIndex = 0; secondaryIndex < this.values[0].length; secondaryIndex++)
				{
					total += this.values[leftRow][secondaryIndex] * right.values[secondaryIndex][rightColumn];
				}

				result.values[leftRow][rightColumn] = total;
			}
		}

		return result;
	}

	public Matrix minus(Matrix other)
	{
		if (this.values.length != other.values.length ||
			this.values[0].length != other.values[0].length)
		{
			throw new IllegalArgumentException();
		}

		Matrix result = new Matrix(this.values.length, this.values[0].length);
		for (int row = 0; row < this.values.length; row++)
		{
			for (int col = 0; col < this.values[0].length; col++)
			{
				result.values[row][col] = this.values[row][col] - other.values[row][col];
			}
		}

		return result;
	}

	public Matrix sigmoid()
	{
		Matrix result = new Matrix(this.values.length, this.values[0].length);
		for (int i = 0; i < this.values.length; i++)
		{
			for (int j = 0; j < this.values[0].length; j++)
			{
				result.values[i][j] = sigmoidValue(this.values[i][j]);
			}
		}

		return result;
	}

	public int elementCount()
	{
		return this.values.length * this.values[0].length;
	}

	private static double sigmoidValue(double original)
	{
		return 1.0 / (1 + Math.pow(Math.E, -original));
	}
}