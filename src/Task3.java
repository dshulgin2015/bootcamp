import com.milaboratory.core.alignment.AffineGapAlignmentScoring;
import com.milaboratory.core.alignment.Aligner;
import com.milaboratory.core.alignment.Alignment;
import com.milaboratory.core.io.sequence.SingleRead;
import com.milaboratory.core.io.sequence.fastq.SingleFastqReader;
import com.milaboratory.core.sequence.Alphabet;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.core.sequence.Seq;
import com.milaboratory.core.sequence.Sequence;

import java.io.File;
import java.io.IOException;

/**
 * Created by user on 27.11.15.
 */
public class Task3 {

    public static final NucleotideSequence GERMLINE_SEQ = new NucleotideSequence("caggtgcagctgcaggagtcgggcccaggactggtgaagccttcggagaccctgtccctc" +
                                              "acctgcgctgtctctggttactccatcagcagtggttactactggggctggatccggcag" +
                                              "cccccagggaaggggctggagtggattgggagtatctatcatagtgggagcacctactac" +
                                              "aacccgtccctcaagagtcgagtcaccatatcagtagacacgtccaagaaccagttctcc" +
                                              "ctgaagctgagctctgtgaccgccgcagacacggccgtgtattactgtgcgaga");


    public static void main(String[] args) throws IOException {
        SingleFastqReader reader = new SingleFastqReader(new File("../bootcamp/task3/sample.fastq"));
        SingleRead read;
        while ((read = reader.take()) != null){
            Alignment alignment = Aligner.alignLocalAffine(
                    new AffineGapAlignmentScoring(NucleotideSequence.ALPHABET, 1, -3, -6, -1),
                    GERMLINE_SEQ, read.getData().getSequence()); // performs local alignment
            System.out.println(alignment.getAbsoluteMutations()); // gets the mutation set
        }
    }
}
