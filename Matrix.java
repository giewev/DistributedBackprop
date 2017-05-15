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

	public String dimensions()
	{
		return this.values.length + "x" + this.values[0].length;
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
			throw new IllegalArgumentException(this.values.length + "x" + this.values[0].length + " * " + right.values.length + "x" + right.values[0].length);
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

	public Matrix flatMultiply(Matrix right)
	{
		Matrix result = new Matrix(1, this.values[0].length);
		for (int i = 0; i < this.values[0].length; i++)
		{
			result.values[0][i] = this.values[0][i] * right.values[0][i];
		}

		return result;
	}

	public Matrix abs()
	{
		Matrix result = new Matrix(this.values.length, this.values[0].length);
		for (int i = 0; i < this.values.length; i++)
		{
			for (int j = 0; j < this.values[0].length; j++)
			{
				result.values[i][j] = Math.abs(this.values[i][j]);
			}
		}

		return result;
	}

	public double sum()
	{
		double total = 0;
		for (int i = 0; i < this.values.length; i++)
		{
			for (int j = 0; j < this.values[0].length; j++)
			{
				total += this.values[i][j];
			}
		}

		return total;
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

	public Matrix sigmoidDerivative()
	{
		Matrix result = new Matrix(this.values.length, this.values[0].length);
		for (int i = 0; i < this.values.length; i++)
		{
			for (int j = 0; j < this.values[0].length; j++)
			{
				result.values[i][j] = this.values[i][j] * (1 - this.values[i][j]);
			}
		}

		return result;
	}

	public int elementCount()
	{
		return this.values.length * this.values[0].length;
	}

	public void randomize()
	{
		for (int i = 0; i < this.values.length; i++)
		{
			for (int j = 0; j < this.values[0].length; j++)
			{
				this.values[i][j] = Math.random() - 0.5;
			}
		}
	}

	public Matrix withBiasColumn()
	{
		Matrix result = new Matrix(this.values.length, this.values[0].length + 1);
		for (int i = 0; i < this.values.length; i++)
		{
			for (int j = 0; j < this.values[0].length; j++)
			{
				result.values[i][j] = this.values[i][j];
			}

			result.values[i][this.values[0].length] = 1;
		}

		return result;
	}

	public Matrix withoutBiasColumn()
	{
		Matrix result = new Matrix(this.values.length, this.values[0].length - 1);
		for (int i = 0; i < result.values.length; i++)
		{
			for (int j = 0; j < result.values[0].length; j++)
			{
				result.values[i][j] = this.values[i][j];
			}
		}

		return result;
	}

	private static double sigmoidValue(double original)
	{
		return 1.0 / (1 + Math.pow(Math.E, -original));
	}
}