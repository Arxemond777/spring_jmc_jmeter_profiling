# spring_jmc_jmeter_profiling

```bash
-Xmx1g -Xms1g -verbose:gc -XX:+PrintGC -XX:+HeapDumpOnOutOfMemoryError -XX:+UseConcMarkSweepGC -XX:HeapDumpPath="./memleaktest.hprof" -XX:OnOutOfMemoryError="kill -9 %p"
```
http://www.jxray.com/download/
```bash
sh jxray.sh ~/IdeaProjects/spring_jmc_jmeter_profiling/memleaktest.hprof ~/IdeaProjects/spring_jmc_jmeter_profiling/memleaks.html
```