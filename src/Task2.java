import com.antigenomics.vdjtools.io.SampleFileConnection;
import com.antigenomics.vdjtools.sample.Clonotype;
import com.antigenomics.vdjtools.sample.Sample;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.tree.NeighborhoodIterator;
import com.milaboratory.core.tree.SequenceTreeMap;
import com.milaboratory.core.tree.TreeSearchParameters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 24.11.15.
 */
public class Task2 {


    public static void main(String[] args) throws FileNotFoundException {

        // Load sample
        Sample sample = SampleFileConnection.load("../bootcamp/task2/sample.txt");

        //Create index
        SequenceTreeMap<AminoAcidSequence, Clonotype> stm = new SequenceTreeMap(AminoAcidSequence.ALPHABET);

        ArrayList<Clonotype> clonotypes = new ArrayList<>();

        for (Clonotype clonotype : sample) {


            if (clonotype.isCoding()) { // ensure CDR3 sequence can be translated (!)

                // Update index
                stm.put(clonotype.getCdr3aaBinary(), clonotype);
               // System.out.println(stm.get(clonotype.getCdr3aaBinary()).getCdr3aa());
                clonotypes.add(clonotype);

            }

        }








        // Query index
        TreeSearchParameters treeSearchParameters = new TreeSearchParameters(2,1,1,2); // set the number of matches, etc for fuzzy search

        File output = new File("../bootcamp/task2/output.txt");

        PrintWriter printer = new PrintWriter(output);

        for (Clonotype clonotype : stm.values()){

                NeighborhoodIterator ni = stm.getNeighborhoodIterator(clonotype.getCdr3aaBinary(), treeSearchParameters);
                Clonotype c = (Clonotype) ni.next();

                while (c != null) {

                    printer.write(clonotype.getCdr3aa() + " " + c.getCdr3aaBinary()+ "\t\n");
                    printer.flush();

                    c = (Clonotype) ni.next();


                }

        }



    }
}
