package org.ansj.lucene;

import java.io.IOException;
import java.io.Reader;
import java.util.Set;
import org.ansj.domain.*;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.*;

public class AnsjTokenizer extends Tokenizer {
  
  private CharTermAttribute termAtt;
  private OffsetAttribute offsetAtt;
  private PositionIncrementAttribute positionAttr;
  private ToAnalysis ta;
  private Set filter;
  private final PorterStemmer stemmer = new PorterStemmer();
  
  public AnsjTokenizer(Reader input, Set filter) {
    super(input);
    ta = null;
    ta = new ToAnalysis(input);
    termAtt = (CharTermAttribute) addAttribute(CharTermAttribute.class);
    offsetAtt = (OffsetAttribute) addAttribute(OffsetAttribute.class);
    positionAttr = (PositionIncrementAttribute) addAttribute(PositionIncrementAttribute.class);
    this.filter = filter;
  }
  
  public boolean incrementToken() throws IOException {
    clearAttributes();
    int position = 0;
    Term term = null;
    String name = null;
    int length = 0;
    do {
      term = ta.next();
      if (term == null) break;
      length = term.getName().length();
      if (term.getTermNatures().termNatures[0] == TermNature.EN) {
        name = stemmer.stem(term.getName());
        term.setName(name);
      }
      position++;
    } while (filter != null && term != null && filter.contains(term.getName()));
    if (term != null) {
      positionAttr.setPositionIncrement(position);
      termAtt.copyBuffer(term.getName().toCharArray(), 0, term.getName()
          .length());
      offsetAtt.setOffset(term.getOffe(), term.getOffe() + length);
      return true;
    } else {
      end();
      return false;
    }
  }
  
}
