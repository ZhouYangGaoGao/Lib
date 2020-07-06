package base;

import java.util.ArrayList;

public class BResponse<T> {

    /**
     * code : 1
     * msg : 操作成功
     * data : af0b63dfa160c08b4905d21423e66c96
     * success : true
     */

    private String code;
    private String msg;
    private T data;
    private boolean success;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "Model='" + getModelName() + '\'' +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", success=" + success +
                '}';
    }

    public String getModelName() {
        if (data == null)
            return null;
        if (data.getClass().getSimpleName().equals("BList")) {
            BList listModel = ((BList) (data));
            if (listModel != null && listModel.getList() != null && listModel.getList().size() > 0) {
                return listModel.getList().get(0).getClass().getSimpleName();
            }
        }

        if (data.getClass().getSimpleName().equals("ArrayList")) {
            ArrayList listModel = ((ArrayList) (data));
            if (listModel != null && listModel.size() > 0) {
                return listModel.get(0).getClass().getSimpleName();
            }
        }
        return data.getClass().getSimpleName();
    }
}
