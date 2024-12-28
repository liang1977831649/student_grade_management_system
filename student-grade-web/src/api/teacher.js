import request from "@/utils/request.js";

export const searchServer= async (searchBody)=>{
    return await request.get("/teacher/list",{
        params:{
            pageNum:searchBody.value.pageNum,
            pageSize:searchBody.value.pageSize,
            name:searchBody.value.name,
            no:searchBody.value.no
        }
     })
}
export const addServer=async(teacherModel)=>{
    await request.post("/teacher",teacherModel.value);
}
export const editServer=async(teacherModel)=>{
    await request.put("/teacher",teacherModel.value); 
}
export const deleteServer=async(id)=>{
    await request.delete("/teacher?id="+id);
}