import org.apache.hadoop.io.WritableComparable;
import java.io.DataOutput;
import java.io.DataInput;
import java.io.IOException;

public class DataSample implements WritableComparable<DataSample>
{
	public double[] inputs;
	public double[] outputs;

	public void write(DataOutput out) throws IOException {
		out.writeInt(this.inputs.length);
		for (int i = 0; i < this.inputs.length; i++)
		{
			out.writeDouble(this.inputs[i]);
		}

		out.writeInt(this.outputs.length);
		for (int i = 0; i < this.outputs.length; i++)
		{
			out.writeDouble(this.outputs[i]);
		}
	}

	public void readFields(DataInput in) throws IOException {
		this.inputs = new double[in.readInt()];
		for (int i = 0; i < this.inputs.length; i++)
		{
			this.inputs[i] = in.readDouble();
		}

		this.outputs = new double[in.readInt()];
		for (int i = 0; i < this.outputs.length; i++)
		{
			this.outputs[i] = in.readDouble();
		}
	}

	public int compareTo(DataSample other) 
	{
		if (this.inputs.length != other.inputs.length)
		{
			return this.inputs.length - other.inputs.length;
		}
		else
		{
			for (int i = 0; i < this.inputs.length; i++)
			{
				if (this.inputs[i] != other.inputs[i])
				{
					return this.inputs[i] - other.inputs[i];
				}
			}
		}

		return 0;
	}

	public int hashCode() {
		int inputHash = 0;
		for (int i = 0; i < this.inputs.length; i++)
		{
			inputHash ^= this.inputs[i];
		}

		return this.inputs.length * 31 ^ this.outputs.length * 37 ^ inputHash;
	}
}