package org.elasticsearch.index.analysis;

import org.ansj.lucene.AnsjAnalyzer;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.settings.IndexSettings;

/**
 * @author laigood
 */
public class AnsjAnalyzerProvider extends
    AbstractIndexAnalyzerProvider<AnsjAnalyzer> {
  private final AnsjAnalyzer analyzer;
  
  @Inject
  public AnsjAnalyzerProvider(Index index,
      @IndexSettings Settings indexSettings, Environment env,
      @Assisted String name, @Assisted Settings settings) {
    super(index, indexSettings, name, settings);
    analyzer = new AnsjAnalyzer(indexSettings);
  }
  
  public AnsjAnalyzerProvider(Index index, Settings indexSettings, String name,
      Settings settings) {
    super(index, indexSettings, name, settings);
    analyzer = new AnsjAnalyzer(indexSettings);
  }
  
  public AnsjAnalyzerProvider(Index index, Settings indexSettings,
      String prefixSettings, String name, Settings settings) {
    super(index, indexSettings, prefixSettings, name, settings);
    analyzer = new AnsjAnalyzer(indexSettings);
  }
  
  @Override
  public AnsjAnalyzer get() {
    return this.analyzer;
  }
}
