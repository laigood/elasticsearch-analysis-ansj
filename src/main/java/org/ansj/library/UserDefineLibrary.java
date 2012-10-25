package org.ansj.library;

import java.io.BufferedReader;
import java.io.File;

import org.ansj.domain.Forest;
import org.ansj.util.IOUtil;
import org.ansj.util.MyStaticValue;
import org.ansj.util.StringUtil;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;

public class UserDefineLibrary {
  public static Forest FOREST = null;
  private static ESLogger logger=null;
  public static void Init(Settings settings) {
    logger = Loggers.getLogger("ansj-analyzer");
    Environment environment = new Environment(settings);

    File userDic = new File(environment.configFile(),
        MyStaticValue.userDefinePath);
    try {
      
      long start = System.currentTimeMillis();
      FOREST = new Forest();
      
      // 先加载系统内置补充词典
      BufferedReader br = MyStaticValue.getUserDefineReader();
      String temp = null;
      while ((temp = br.readLine()) != null) {
        if (StringUtil.isBlank(temp)) {
          continue;
        } else {
          Library.insertWord(FOREST, temp);
        }
      }
      
      // 如果系统设置了用户词典.那么..呵呵
      // 加载用户自定义词典
      if (userDic.isFile()) {
        temp = userDic.getAbsolutePath();
        br = IOUtil.getReader(temp, "UTF-8");
        while ((temp = br.readLine()) != null) {
          if (StringUtil.isBlank(temp)) {
            continue;
          } else {
            Library.insertWord(FOREST, temp);
          }
        }
      } else {
        
        System.err.println("用户自定义词典:" + MyStaticValue.userDefinePath
            + ", 没有这个文件!");
      }
      logger.info("[Dict Loading] {},UserDict Time:{}",userDic.toString(), (System.currentTimeMillis() - start));
    } catch (Exception e) {
      logger.info("[Dict Loading] {},Load error!",userDic.toString());
    }
    
  }
  
  /**
   * 增加关键词
   */
  public static void insertWord(String temp) {
    Library.insertWord(FOREST, temp);
  }
  
  /**
   * 删除关键词
   */
  
  public static void removeWord(String word) {
    Library.removeWord(FOREST, word);
  }
}
