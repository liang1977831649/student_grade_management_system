import request from "@/utils/request.js";

export const updateAdminPasswordServer=async (passwordBody)=>{
    const param=new URLSearchParams;
    console.log("passwordBody.value=",passwordBody.value);
    for(let key in passwordBody.value){
        param.append(key,passwordBody.value[key])
    }
    await request.post('/admin/password',param)
}