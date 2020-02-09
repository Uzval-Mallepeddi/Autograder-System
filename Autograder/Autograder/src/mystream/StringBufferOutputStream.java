package mystream;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class StringBufferOutputStream extends PrintStream 
{
	public StringBufferOutputStream(OutputStream out) {
		super(out);
		// TODO Auto-generated constructor stub
	}
    
			// the target buffer
	        protected StringBuffer buffer;
	    
	        /**
	         * Create an output stream that writes to the target StringBuffer
	         *
	         * @param out    The wrapped output stream.
	         */
	        
//	        public StringBufferOutputStream(StringBuffer out) {
//	        	super();
//	            buffer = out;
//	        }
//	    
//	    
//	        public StringBufferOutputStream(OutputStream out) {
//				super(out);
//			}
//

			// in order for this to work, we only need override the single character form, as the others
	        // funnel through this one by default.
	        public void write(int ch) {
	            // just append the character
	            buffer.append((char)ch);
	        }
}
