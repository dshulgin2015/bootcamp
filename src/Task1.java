import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import com.milaboratory.core.alignment.KAligner;
import com.milaboratory.core.alignment.KAlignerParameters;
import com.milaboratory.core.alignment.KAlignmentResult;
import com.milaboratory.core.io.sequence.SingleRead;
import com.milaboratory.core.io.sequence.fastq.SingleFastqReader;
import com.milaboratory.core.sequence.NucleotideSequence;

/**
 * Created by user on 24.11.15.
 */
public class Task1 {

    public static final String delimiters = "\\s+|,\\s*|\\.\\s*";

    public static void main(String[] args) throws IOException {

        //initialize vars

        SingleFastqReader reader = new SingleFastqReader(new File("/home/user/sample.fastq"));
        SingleRead read;
        File segments = new File("../bootcamp/task1/segments.txt");
        Scanner sc = new Scanner(segments);
        String [] splitArray;
        HashMap<String, String> junctions = new HashMap<String, String>();

        KAligner V_aligner = new KAligner(KAlignerParameters.getByName("default")); // Variable aligner
        KAligner J_aligner = new KAligner(KAlignerParameters.getByName("default")); // Joining aligner

        sc.nextLine();                                                          //skip title

        while (sc.hasNextLine()){
            splitArray = sc.nextLine().split(delimiters);

            if(splitArray.length > 1) {
                junctions.put(splitArray[3], splitArray[5]);

                if (splitArray[3].contains("V")){
                    V_aligner.addReference(new NucleotideSequence(splitArray[5]), splitArray[3]);
                } else{
                    J_aligner.addReference(new NucleotideSequence(splitArray[5]), splitArray[3]);
                }
            }
        }

        while ((read = reader.take()) != null){

            KAlignmentResult result = V_aligner.align(read.getData().getSequence());


            if(result.getBestHit()!= null){
                System.out.println(String.valueOf(result.getBestHit().getRecordPayload()));
            }

        }

    }

}

