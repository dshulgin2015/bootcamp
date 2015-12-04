import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import com.milaboratory.core.alignment.KAligner;
import com.milaboratory.core.alignment.KAlignerParameters;
import com.milaboratory.core.alignment.KAlignmentResult;
import com.milaboratory.core.io.sequence.SingleRead;
import com.milaboratory.core.io.sequence.fastq.SingleFastqReader;
import com.milaboratory.core.sequence.NucleotideSequence;
import org.apache.commons.math3.util.Pair;

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
        HashMap<String, String> segmentsMap = new HashMap<String, String>();

        KAligner V_aligner = new KAligner(KAlignerParameters.getByName("default")); // Variable aligner
        KAligner J_aligner = new KAligner(KAlignerParameters.getByName("default")); // Joining aligner

        sc.nextLine();                                                          //skip title

        while (sc.hasNextLine()){
            splitArray = sc.nextLine().split(delimiters);

            if(splitArray.length > 1) {
                segmentsMap.put(splitArray[3], splitArray[5]);

                if (splitArray[3].contains("V")){
                    V_aligner.addReference(new NucleotideSequence(splitArray[5]), splitArray[3]);
                } else{
                    J_aligner.addReference(new NucleotideSequence(splitArray[5]), splitArray[3]);
                }
            }
        }

        Set<Pair<String,String>> junctions = new HashSet<>();
        while ((read = reader.take()) != null){

            KAlignmentResult Vresult = V_aligner.align(read.getData().getSequence());
            KAlignmentResult Jresult = J_aligner.align(read.getData().getSequence());

            if(Vresult.getBestHit()!= null && Jresult.getBestHit() != null){
                junctions.add(new Pair<String, String>(Vresult.getBestHit().getRecordPayload().toString(),Jresult.getBestHit().getRecordPayload().toString()));

            }

        }

        PrintWriter printWriter = new PrintWriter(new File("../bootcamp/task1/output.txt"));

        for (Pair<String, String> junction:junctions
                ) {

            printWriter.write(junction.getFirst()+ " " + junction.getSecond() + "\t\n");
            printWriter.flush();

        }

    }

}

