import request from "@/utils/request.js";

export const getUserInfoServer= async()=>{
    const result= await request.get("/info");
    return result;
}
