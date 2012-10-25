
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Date;
import java.util.HashSet;
import java.util.ResourceBundle;

import org.ansj.lucene.AnsjAnalyzer;
import org.ansj.lucene.PorterStemmer;
import org.ansj.util.IOUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class AnsjAnalysisTest {
  
  public AnsjAnalysisTest() {
    ansjHeightAnalyzer = new AnsjAnalyzer();
  }
  
  public static void main(String[] args) throws IOException{
    Analyzer ca = new AnsjAnalyzer();
    Reader sentence = new StringReader(
        "Economist Intelligence Unit全球技术研究主管Denis MaCauley一直致力于研究IT及增强现实的未来趋势，增强现实是备受关注的领域之一，主要是利用计算机生成数据来增强现实、真实世界环境的体验。MaCauley表示增强的例子可能包括增加信息，比如向体育广播中添加事实和数字。“你拥有的一些东西是不能完全虚拟的，但可以利用计算机生成数据增强观赏体验。”他讲到。企业和厂商希望在新领域进行投资，Juniper研究表明移动增强现实的收入在2015年将会达到15亿美元。MaCauley说，一些CIO相信增强现实能够在商业的特定领域出现可用的应用程序，如客服服务和员工培训。");
    TokenStream ts = ca.tokenStream("sentence", sentence);
    System.out.println((new StringBuilder("start: ")).append(new Date())
        .toString());
    long before = System.currentTimeMillis();
    while(ts.incrementToken()){
    }
        ts.close();
    long now = System.currentTimeMillis();
    System.out.println((new StringBuilder("time: "))
        .append((double) (now - before) / 1000D).append(" s").toString());
  }
  
  public void indexTest() throws CorruptIndexException,
      LockObtainFailedException, IOException, ParseException {
    HashSet hs = new HashSet();
    BufferedReader reader2 = IOUtil.getReader(
        ResourceBundle.getBundle("library").getString("stopLibrary"), "UTF-8");
    for (String word = null; (word = reader2.readLine()) != null;)
      hs.add(word);
    
    Analyzer analyzer = new AnsjAnalyzer(hs);
    Directory directory = null;
    IndexWriter iwriter = null;
    BufferedReader reader = IOUtil
        .getReader(
            "/Users/ansj/Desktop/\u672A\u547D\u540D\u6587\u4EF6\u5939/indextest.txt",
            "UTF-8");
    String temp = null;
    StringBuilder sb = new StringBuilder();
    while ((temp = reader.readLine()) != null) {
      sb.append(temp);
      sb.append("\n");
    }
    reader.close();
    String text = sb.toString();
    text = "\u5F00\u6E90\u9879\u76EE\u7BA1\u7406\u4F60\u559C\u6B22\u5728\u5BA4\u5916\u5DE5\u4F5C\u5417\uFF1F\u4F60\u662F\u4E2A\u559C\u6B22\u601D\u8003\u7684\u4EBA\u5417\uFF1F\u4F60\u559C\u6B22\u6570\u5B66\u548C\u79D1\u5B66\u8BFE\u5417\uFF1F\u4F60\u559C\u6B22\u4E00\u4E2A\u4EBA\u5DE5\u4F5C\u5417\uFF1F\u4F60\u5BF9\u81EA\u5DF1\u7684\u667A\u529B\u81EA\u4FE1\u5417\uFF1F\u4F60\u7684\u521B\u9020\u80FD\u529B\u5F88\u5F3A\u5417\uFF1F\u4F60\u559C\u6B22\u827A\u672F\uFF0C\u97F3\u4E50\u548C\u620F\u5267\u5417\uFF1F  \u4F60\u559C\u6B22\u81EA\u7531\u81EA\u5728\u7684\u5DE5\u4F5C\u73AF\u5883\u5417\uFF1F\u4F60\u559C\u6B22\u5C1D\u8BD5\u65B0\u7684\u4E1C\u897F\u5417\uFF1F \u4F60\u559C\u6B22\u5E2E\u52A9\u522B\u4EBA\u5417\uFF1F\u4F60\u559C\u6B22\u6559\u522B\u4EBA\u5417\uFF1F\u4F60\u559C\u6B22\u548C\u673A\u5668\u548C\u5DE5\u5177\u6253\u4EA4\u9053\u5417\uFF1F\u4F60\u559C\u6B22\u5F53\u9886\u5BFC\u5417\uFF1F\u4F60\u559C\u6B22\u7EC4\u7EC7\u6D3B\u52A8\u5417\uFF1F\u4F60\u4EC0\u4E48\u548C\u6570\u5B57\u6253\u4EA4\u9053\u5417\uFF1F";
    IndexWriterConfig ic = new IndexWriterConfig(Version.LUCENE_32, analyzer);
    directory = new RAMDirectory();
    iwriter = new IndexWriter(directory, ic);
    addContent(iwriter, text);
    iwriter.commit();
    iwriter.close();
    System.out.println("\u7D22\u5F15\u5EFA\u7ACB\u5B8C\u6BD5");
    search(analyzer, directory,
        "java\u591A\u7EBF\u7A0B\u5904\u7406\u4F8B\u5B50");
  }
  
  private void search(Analyzer analyzer, Directory directory, String queryStr)
      throws CorruptIndexException, IOException, ParseException {
    IndexSearcher isearcher = new IndexSearcher(directory);
    QueryParser tq = new QueryParser(Version.LUCENE_32, "text",
        ansjHeightAnalyzer);
    Query query = tq.parse(queryStr);
    System.out.println(query);
    TopDocs hits = isearcher.search(query, 5);
    System.out.println((new StringBuilder(String.valueOf(queryStr)))
        .append(":\u5171\u627E\u5230").append(hits.totalHits)
        .append("\u6761\u8BB0\u5F55!").toString());
    for (int i = 0; i < hits.scoreDocs.length; i++) {
      int docId = hits.scoreDocs[i].doc;
      Document document = isearcher.doc(docId);
      System.out.println(toHighlighter(ansjHeightAnalyzer, query, document));
    }
    
  }
  
  private String toHighlighter(Analyzer analyzer, Query query, Document doc) {
    String field = "text";
    try {
      SimpleHTMLFormatter simpleHtmlFormatter = new SimpleHTMLFormatter(
          "<font color=\"red\">", "</font>");
      Highlighter highlighter = new Highlighter(simpleHtmlFormatter,
          new QueryScorer(query));
      TokenStream tokenStream1 = analyzer.tokenStream("text", new StringReader(
          doc.get(field)));
      String highlighterStr = highlighter.getBestFragment(tokenStream1,
          doc.get(field));
      return highlighterStr != null ? highlighterStr : doc.get(field);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InvalidTokenOffsetsException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  private void addContent(IndexWriter iwriter, String text)
      throws CorruptIndexException, IOException {
    Document doc = new Document();
    doc.add(new Field("text", text, org.apache.lucene.document.Field.Store.YES,
        org.apache.lucene.document.Field.Index.ANALYZED));
    iwriter.addDocument(doc);
  }
  
  public void poreterTest() {
    PorterStemmer ps = new PorterStemmer();
    System.out.println(ps.stem("apache"));
  }
  
  private Analyzer ansjHeightAnalyzer;
}
