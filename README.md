Ansj Analysis for ElasticSearch
==================================

The Ansj Analysis plugin(https://github.com/ansjsun/ansj_seg) integrates Lucene Ansj analyzer into elasticsearch, support customized dictionary.


Version
-------------
 master                      | 0.19.4 -> master   
 1.0.0                       | 0.19.4 -> master   


Install
-------------

In order to install the plugin, simply run:
 
<pre>
cd bin
plugin -install laigood/elasticsearch-analysis-ansj/1.0.0
</pre>
 
also download the dict files,unzip these dict file to your elasticsearch's config folder,such as: your-es-root/config/ansj

<pre>
cd config
wget http://github.com/downloads/laigood/elasticsearch-analysis-ansj/ansj.zip --no-check-certificate
unzip ansj.zip
rm ansj.zip
</pre>

you need a service restart after that!

Dict Configuration (es-root/config/ansj/userLibrary.dic)

Analysis Configuration (elasticsearch.yml)
-------------

<Pre>
index:
  analysis:                   
    analyzer:      
      ansj:
          alias: [ansj_analyzer]
          type: org.elasticsearch.index.analysis.AnsjAnalyzerProvider
</pre>
Or
<pre>
index.analysis.analyzer.ik.type : "ansj"
</pre>

