package demofour;

import android.os.RemoteException;

import com.example.kys_31.study_demo.ISecurityCenter;

/**
 * Created by 老头儿 on 2017/9/19.
 * @function 这个包中讲解的主要是Binder连接池，用于避免多个任务模块造成开启多个Service
 */

public class SecurityCenterImpl extends ISecurityCenter.Stub {

    private static final char SECRET_CODE = '^';

    @Override
    public String encrypt(String content) throws RemoteException {
        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++){
            chars[i] ^= SECRET_CODE;
        }
        return new String(chars);
    }

    @Override
    public String decrypt(String password) throws RemoteException {
        return encrypt(password);
    }
}
