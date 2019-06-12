package com.hbsd.rjxy.lunwen.ljz.login.service;

import com.hbsd.rjxy.lunwen.entity.Major;
import com.hbsd.rjxy.lunwen.entity.Student;
import com.hbsd.rjxy.lunwen.ljz.login.dao.LoginStudentRepository;
import com.hbsd.rjxy.lunwen.util.crawler.Aexp;
import com.hbsd.rjxy.lunwen.util.crawler.Score;
import com.hbsd.rjxy.lunwen.util.crawler.ZFsoft;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class LoginStudentService {
    @Autowired
    private ZFsoft zFsoft;
    @Autowired
    private Aexp aexp;
    @Autowired
    private LoginStudentRepository loginStudentRepository;
    @Autowired
    private LoginMajorService loginMajorService;
    @Transactional
    public void updateStudent(Student student){
        if(student!=null){
            loginStudentRepository.save(student);
        }
        return;
    }
    public Student findBySId(String sId){
        if(sId!=null){
            Optional<Student>optional= loginStudentRepository.findById(sId);
            return optional.get();
        }
        return null;
    }
    /**
     *
     * @param sId
     * @param sPwd
     * @return{
     *      -0用户不在数据库中 false
     *      -1首次登录 成功爬取数据 存入数据库 跳转添加必要信息
     *      -2首次登录 爬取数据失败 密码错误 || 二次登录 密码错误
     *      -3二次登录 密码正确
     * }
     */
    @Transactional
    public int login(String sId,String sPwd){
        //判断用户是否在数据库中
        if(loginStudentRepository.existsById(sId)) {
            Optional<Student>optional= loginStudentRepository.findById(sId);
            Student student=optional.get();
            //判断首次或二次登录
            if(student.getTimes()==0){
                ZFsoft user=zFsoft.login(sId, sPwd);
                if(user!=null){
                    String infoJsonStr=user.getInfos("","");
                    Double scScore=aexp.getScore(sId, sPwd);
                    if(scScore!=null){
                        try {
                            student.setScScore(scScore);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        student.setScScore(null);
                    }
                    JSONObject jsonObject=JSONObject.fromObject(infoJsonStr);
                    String mName=jsonObject.getString("major");
                    Major major= loginMajorService.findByMName(mName);
                    student.setMajor(major);
                    String grade=jsonObject.getString("grade");
                    student.setGrade(grade);
                    if(jsonObject.has("score")){
                        JSONArray scores=jsonObject.getJSONArray("score");
                        Map<String, ArrayList<Double>>reqMap=new HashMap<>(),optMap=new HashMap<>();
                        for (Object object:
                             scores) {
                            JSONObject scoreObj=(JSONObject)object;
                            Score score=(Score) JSONObject.toBean(scoreObj,Score.class);
                            String kch=score.getKch();
                            if(score.getKcxzmc().equals("必修")){
                                if(!reqMap.containsKey(kch)){
                                    ArrayList<Double>list=new ArrayList<>();
                                    list.add(0,Double.parseDouble(score.getXf()));
                                    list.add(1,Double.parseDouble(score.getJd()));
                                    if(score.getCj().equals("缺考")||score.getCj().equals("补考")){
                                        list.add(2,new Double(0));
                                    }else{
                                        list.add(2,Double.parseDouble(score.getCj()));
                                    }
                                    reqMap.put(kch,list);
                                }else{
                                    if(reqMap.get(kch).get(2).compareTo(Double.parseDouble(score.getCj()))<0){
                                        reqMap.get(kch).add(1,Double.parseDouble(score.getJd()));
                                        reqMap.get(kch).add(2,Double.parseDouble(score.getCj()));
                                    }
                                }
                            }else if(score.getKcxzmc().equals("选修")&&score.getKclbmc().equals("专业平台课程")){
                                if(!optMap.containsKey(kch)){
                                    ArrayList<Double>list=new ArrayList<>();
                                    list.add(0,Double.parseDouble(score.getXf()));
                                    list.add(1,Double.parseDouble(score.getJd()));
                                    list.add(2,Double.parseDouble(score.getCj()));
                                    optMap.put(kch,list);
                                }else{
                                    if(optMap.get(kch).get(2).compareTo(Double.parseDouble(score.getCj()))<0){
                                        optMap.get(kch).add(1,Double.parseDouble(score.getJd()));
                                        optMap.get(kch).add(2,Double.parseDouble(score.getCj()));
                                    }
                                }
                            }
                        }
                        double sum=0,count=0;
                        for (String key:
                             reqMap.keySet()) {
                            count+=reqMap.get(key).get(0);
                            sum+=reqMap.get(key).get(0)*reqMap.get(key).get(1);
                        }
                        student.setOblJd(new BigDecimal(sum/count)
                                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        sum=0;count=0;
                        for (String key:
                             optMap.keySet()) {
                            count+=optMap.get(key).get(0);
                            sum+=optMap.get(key).get(0)*optMap.get(key).get(2);
                        }
                        student.setEleScore(new BigDecimal(sum/count)
                                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    }
                    student.setTimes(1);
                    student.setsPwd(sPwd);
                    student.setSubState(0);
                    loginStudentRepository.save(student);
                    return 1;
                }else{
                    return 2;
                }
            }else{
                //二次登录
                if(sPwd.equals(student.getsPwd())){
                    //密码正确
                    return 3;
                }else{
                    //密码错误
                    return 2;
                }
            }
        }else{
            return 0;
        }
    }
}
