import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.milaboratory.core.alignment.AffineGapAlignmentScoring;
import com.milaboratory.core.alignment.Aligner;
import com.milaboratory.core.alignment.Alignment;
import com.milaboratory.core.io.sequence.SingleRead;
import com.milaboratory.core.io.sequence.fastq.SingleFastqReader;
import com.milaboratory.core.mutations.Mutation;
import com.milaboratory.core.mutations.Mutations;
import com.milaboratory.core.mutations.MutationsUtil;
import com.milaboratory.core.sequence.Alphabet;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.core.sequence.Seq;
import com.milaboratory.core.sequence.Sequence;
import org.apache.commons.math3.util.Pair;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;




/**
 * Created by user on 27.11.15.
 */
public class Task3 {


    public static Set<String> mutations = new HashSet<>();

    public static final NucleotideSequence GERMLINE_SEQ = new NucleotideSequence("caggtgcagctgcaggagtcgggcccaggactggtgaagccttcggagaccctgtccctc" +
                                              "acctgcgctgtctctggttactccatcagcagtggttactactggggctggatccggcag" +
                                              "cccccagggaaggggctggagtggattgggagtatctatcatagtgggagcacctactac" +
                                              "aacccgtccctcaagagtcgagtcaccatatcagtagacacgtccaagaaccagttctcc" +
                                              "ctgaagctgagctctgtgaccgccgcagacacggccgtgtattactgtgcgaga");


    public static void main(String[] args) throws IOException {
        SingleFastqReader reader = new SingleFastqReader(new File("../bootcamp/task3/sample.fastq"));
        SingleRead read;
        Alignment alignment;

        int k = 0;
        while ((read = reader.take()) != null){

             alignment = Aligner.alignLocalAffine(
                    new AffineGapAlignmentScoring(NucleotideSequence.ALPHABET, 1, -3, -6, -1),
                    GERMLINE_SEQ, read.getData().getSequence()); // performs local alignment
             mutations.add(alignment.getAbsoluteMutations().toString().substring(1,alignment.getAbsoluteMutations().toString().length()-1));


            k++;

        }

        //print
        Iterator<String> it = mutations.iterator();

        //computing the graph

        ArrayList<Pair<Set<String>, Set<String>>> pairs = new ArrayList<>();


        System.out.println(mutations.size());


        while (it.hasNext()){

            String s1 = it.next();
            Set<String> setS1 = new HashSet<>(Arrays.asList(s1.split(",")));

            Iterator<String> iter = mutations.iterator();
            Set<String> setS1tpm = new HashSet<>(setS1);

            while (iter.hasNext()){

                String s2 = iter.next();
                Set<String> setS2 = new HashSet<>(Arrays.asList(s2.split(",")));

                if(setS1.size() < setS2.size()){
                    setS1tpm.removeAll(setS2);
                    if (setS1tpm.isEmpty())
                        pairs.add(new Pair<Set<String>, Set<String>>(setS1,setS2));
                }

                setS1tpm.clear();
                setS1tpm.addAll(setS1);
            }
        }

        //getting smallest mutation sets
        System.out.println(pairs.size());

        Set<Set<String>> inner = new HashSet<>();
        Set<Set<String>> other = new HashSet<>();
        for (Pair<Set<String>, Set<String>> pair:pairs
                ) {

            inner.add(pair.getFirst());

        }
        //inner.removeAll(other); // only good mutations sets

        System.out.println(inner.size());


        //Adding intersectionts m# to graph
        for (Set<String> x:inner
             ) {

            for (Set<String> m:inner
                 ) {
                Set<String> tmp = new HashSet<>(x);
                if(!tmp.equals(m)) {
                    tmp.retainAll(m);
                    if (!tmp.isEmpty()) {
                        pairs.add(new Pair<Set<String>, Set<String>>(tmp,m));
                        pairs.add(new Pair<Set<String>, Set<String>>(tmp,x));
                        //System.out.println(mutGraph.size());
                    }
                }

            }

        }

        System.out.println(pairs.size());

        inner.clear();
        for (Pair<Set<String>, Set<String>> pair:pairs
                ) {

            inner.add(pair.getFirst());
            other.add(pair.getSecond());

        }
        inner.removeAll(other);
        System.out.println(inner.toArray()[0].toString());
        PrintWriter printWriter = new PrintWriter(new File("../bootcamp/task3/output.txt"));

        for (Pair<Set<String>, Set<String>> pair:pairs
             ) {

            printWriter.write(pair.getFirst().hashCode() + " " + pair.getSecond().hashCode() + "\t\n");
            printWriter.flush();

        }








    }
}
