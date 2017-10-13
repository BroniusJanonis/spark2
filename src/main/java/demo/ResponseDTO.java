package demo;

public class ResponseDTO {
    private ResponseData responseData;
    private ResponseDATA1 responseDATA1;
    private String name;

    public ResponseDTO() {
    }

    public ResponseDTO(ResponseData responseData, ResponseDATA1 responseDATA1, String name) {
        this.responseData = responseData;
        this.responseDATA1 = responseDATA1;
        this.name = name;
    }

    public ResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    public ResponseDATA1 getResponseDATA1() {
        return responseDATA1;
    }

    public void setResponseDATA1(ResponseDATA1 responseDATA1) {
        this.responseDATA1 = responseDATA1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
