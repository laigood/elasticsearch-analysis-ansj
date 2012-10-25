package org.ansj.lucene;

import java.io.Reader;
import java.util.Set;

import org.ansj.library.UserDefineLibrary;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.elasticsearch.common.settings.Settings;

public class AnsjAnalyzer extends Analyzer {
  
  public AnsjAnalyzer(Set filter) {
    this.filter = filter;
  }
  
  public AnsjAnalyzer() {}

  public AnsjAnalyzer(Settings settings) {
    UserDefineLibrary.Init(settings);
  }
  
  public TokenStream tokenStream(String fieldName, Reader reader) {
    return new AnsjTokenizer(reader, filter);
  }
  
  public Set filter;
}
