package java_jdbc_exer;
/**
 * @author:
 * @contact: 
 * @file: ReflectionUtils.java
 * @time: 2020/3/26 18:57
 * @desc: |JDBC ��ѯ�õ������ֶ� ������Ʒ��ص� JavaBean����ͬ���������Ķ�����
 */

import java.lang.reflect.*;


public class ReflectionUtils {

    /**
     * ʹ filed ��Ϊ�ɷ���
     *
     * @param field
     */
    public static void makeAccessible(Field field) {
        if (!Modifier.isPublic(field.getModifiers())) {
            field.setAccessible(true);
        }
    }

    /**
     * ֱ�����ö�������ԣ����� private/protected ���η�, Ҳ������ setter
     *
     * @param object
     * @param fieldName ��������
     * @param value
     */
    public static void setFieldValue(Object object, String fieldName, Object value) {

        Field field = getDeclaredField(object, fieldName);
        if (field == null)
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");

        // �� private Ԫ�ر�ÿ��Է���,field.setAccessible();
        makeAccessible(field);

        try {
            field.set(object, value);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * ѭ������ת��, ��ȡ����� DeclaredField
     *
     * @param object
     * @param filedName
     * @return
     */
    public static Field getDeclaredField(Object object, String filedName) {
        // һ������ѭ���õ� ��ȡ���������������
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(filedName);
            } catch (NoSuchFieldException | SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * ֱ�Ӷ�ȡ���������ֵ, ���� private/protected ���η�, Ҳ������ getter
     *
     * @param object
     * @param fieldName
     * @return
     */
    public static Object getFieldValue(Object object, String fieldName) {
        Field field = getDeclaredField(object, fieldName);

        if (field == null)
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");

        makeAccessible(field);
        Object result = null;

        try {
            result = field.get(object);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * ѭ������ת��, ��ȡ����� DeclaredMethod
     *
     * @param object
     * @param methodName
     * @param parameterTypes: ָ���ض���Ա���������ؿ����ԣ�����ָ���ض������ͱ���
     * @return
     */
    public static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                //superClass.getMethod(methodName, parameterTypes);
                return superClass.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                //Method ���ڵ�ǰ�ඨ��, ��������ת��
            }
            //..
        }

        return null;
    }

    /**
     * ֱ�ӵ��ö��󷽷�, ���������η�(private, protected)
     *
     * @param object
     * @param methodName
     * @param parameterTypes
     * @param parameters
     * @return
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     */
    public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes,
                                      Object[] parameters) throws InvocationTargetException {
        Method method = getDeclaredMethod(object, methodName, parameterTypes);

        if (method == null)
            throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + object + "]");
        method.setAccessible(true);

        // ʹ�� method.invoke()������������
        try {
            method.invoke(object, parameters);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return method;
    }

    /**
     * ͨ������, ��ö��� Class ʱ�����ĸ���ķ��Ͳ���������
     * ��: public EmployeeDao extends BaseDao<Employee, String>
     *
     * @param clazz
     * @return
     */
    public static Class getSuperClassGenricType(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();

        // �ж�s�Ƿ���ParameterType���Ӧ��
        if (!(genType instanceof ParameterizedType))
            return Object.class;

        // ǿ��ת�� ��ȡ���෺�Ͳ���ʵ�����ͣ�����genType ����Ľӿڣ��������ͻ���void
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0)
            return Object.class;

        if (!(params[index] instanceof Class))
            return Object.class;
        return (Class) params[index];
    }

    /**
     * ͨ������,���Class�����������ĸ���ķ��Ͳ���������.
     * eg.
     * public UserDao extends HibernateDao<User>
     *
     * @param clazz The class to introspect
     * @return the first generic declaration, or Object.class if cannot be determined
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }
}