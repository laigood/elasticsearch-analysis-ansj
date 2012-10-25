package org.elasticsearch.index.analysis;

/**
 * @author laigood
 */
public class AnsjAnalysisBinderProcessor extends
    AnalysisModule.AnalysisBinderProcessor {
  
  @Override
  public void processTokenFilters(TokenFiltersBindings tokenFiltersBindings) {
    
  }
  
  @Override
  public void processAnalyzers(AnalyzersBindings analyzersBindings) {
    analyzersBindings.processAnalyzer("ansj", AnsjAnalyzerProvider.class);
    super.processAnalyzers(analyzersBindings);
  }
  
}
