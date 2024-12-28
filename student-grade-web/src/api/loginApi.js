import request from "@/utils/request.js";

export const loginService = async (loginBody) => {
    const param=new URLSearchParams;
    for(var key in loginBody.value){
        param.append(key,loginBody.value[key])
    }
    return await request.post("/login",param)
    // return await request.post("/student/login",loginBody.value)
};
export const registerServer= async (registerBody)=>{
    request.post("/register",registerBody.value);
}