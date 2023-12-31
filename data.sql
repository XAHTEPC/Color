PGDMP     9    .        
        {            cat2    15.2    15.2 k    q           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            r           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            s           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            t           1262    18058    cat2    DATABASE     x   CREATE DATABASE cat2 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Russian_Russia.1251';
    DROP DATABASE cat2;
                postgres    false            ~           1247    18695 
   newaddress    TYPE     [   CREATE TYPE public.newaddress AS (
	post character varying(100),
	street character(100)
);
    DROP TYPE public.newaddress;
       public          postgres    false            �            1255    18104    update_client_bonus()    FUNCTION     �   CREATE FUNCTION public.update_client_bonus() RETURNS trigger
    LANGUAGE plpgsql
    AS $$BEGIN
 UPDATE client_bonus
 set bonus = bonus + div(new.price, 5) - div(old.price,5)
 where client_id = new.client_id;
 return new;

END;$$;
 ,   DROP FUNCTION public.update_client_bonus();
       public          postgres    false            �            1255    18105    update_client_status()    FUNCTION     �  CREATE FUNCTION public.update_client_status() RETURNS trigger
    LANGUAGE plpgsql
    AS $$ 
BEGIN 
    UPDATE Client SET client_status =  
        CASE  
            WHEN (SELECT COUNT(*) FROM Booking WHERE client_id = NEW.client_id) >= 20 THEN 'VIP' 
            WHEN (SELECT COUNT(*) FROM Booking WHERE client_id = NEW.client_id) >= 3 THEN 'Premium' 
            ELSE 'Обычный' 
        END 
    WHERE id = NEW.client_id; 
    RETURN NEW; 
END; 
$$;
 -   DROP FUNCTION public.update_client_status();
       public          postgres    false            u           0    0    FUNCTION update_client_status()    ACL     R   GRANT ALL ON FUNCTION public.update_client_status() TO "Staff" WITH GRANT OPTION;
          public          postgres    false    239            �            1255    18106    update_employee_rating()    FUNCTION     ?  CREATE FUNCTION public.update_employee_rating() RETURNS trigger
    LANGUAGE plpgsql
    AS $$BEGIN
 UPDATE employee
 set rating = (select sum(master_rating)/count(master_rating)
					  	from booking
					   where id = new.master_id and booking.master_rating <> 0
					  )
where id = new.master_id;
return new;
END;$$;
 /   DROP FUNCTION public.update_employee_rating();
       public          postgres    false            v           0    0 !   FUNCTION update_employee_rating()    ACL     T   GRANT ALL ON FUNCTION public.update_employee_rating() TO "Staff" WITH GRANT OPTION;
          public          postgres    false    252            �            1259    18117    booking    TABLE     �   CREATE TABLE public.booking (
    id_booking integer NOT NULL,
    client_id integer,
    service_id integer,
    booking_date date,
    master_rating numeric(3,2) DEFAULT 0.0,
    master_id integer
);
    DROP TABLE public.booking;
       public         heap    postgres    false            w           0    0    TABLE booking    ACL     �   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.booking TO "Manager" WITH GRANT OPTION;
GRANT SELECT ON TABLE public.booking TO "Analyst" WITH GRANT OPTION;
          public          postgres    false    215            �            1259    18121    booking_id_seq    SEQUENCE     �   CREATE SEQUENCE public.booking_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.booking_id_seq;
       public          postgres    false    215            x           0    0    booking_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.booking_id_seq OWNED BY public.booking.id_booking;
          public          postgres    false    216            �            1259    18122    client    TABLE     �   CREATE TABLE public.client (
    id integer NOT NULL,
    full_name character varying(255),
    client_status character varying(255) DEFAULT 'Обычный'::character varying NOT NULL
);
    DROP TABLE public.client;
       public         heap    postgres    false            y           0    0    TABLE client    ACL     �   GRANT SELECT,INSERT ON TABLE public.client TO "Staff" WITH GRANT OPTION;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.client TO "Manager" WITH GRANT OPTION;
GRANT SELECT ON TABLE public.client TO "Analyst" WITH GRANT OPTION;
          public          postgres    false    217            �            1259    18127    client_bonus    TABLE     r   CREATE TABLE public.client_bonus (
    id integer NOT NULL,
    client_id integer,
    bonus integer DEFAULT 0
);
     DROP TABLE public.client_bonus;
       public         heap    postgres    false            z           0    0    TABLE client_bonus    ACL     �   GRANT SELECT,INSERT,UPDATE ON TABLE public.client_bonus TO "Staff" WITH GRANT OPTION;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.client_bonus TO "Manager" WITH GRANT OPTION;
GRANT SELECT ON TABLE public.client_bonus TO "Analyst" WITH GRANT OPTION;
          public          postgres    false    218            �            1259    18130    client_bonus_id_seq    SEQUENCE     �   CREATE SEQUENCE public.client_bonus_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.client_bonus_id_seq;
       public          postgres    false    218            {           0    0    client_bonus_id_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.client_bonus_id_seq OWNED BY public.client_bonus.id;
          public          postgres    false    219            �            1259    18420    client_bonus_id_seq1    SEQUENCE     �   ALTER TABLE public.client_bonus ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.client_bonus_id_seq1
    START WITH 505
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    218            �            1259    18131    client_id_seq    SEQUENCE     �   CREATE SEQUENCE public.client_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.client_id_seq;
       public          postgres    false    217            |           0    0    client_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.client_id_seq OWNED BY public.client.id;
          public          postgres    false    220            �            1259    18620    client_id_seq1    SEQUENCE     �   ALTER TABLE public.client ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.client_id_seq1
    START WITH 510
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    217            �            1259    18107    employee    TABLE     k  CREATE TABLE public.employee (
    id integer NOT NULL,
    full_name character varying(255),
    "position" character varying(255),
    contact_information character varying(255),
    experience integer,
    salary numeric(10,2),
    brief_information text,
    age integer,
    rating numeric(3,2)
);

ALTER TABLE ONLY public.employee FORCE ROW LEVEL SECURITY;
    DROP TABLE public.employee;
       public         heap    postgres    false            }           0    0    TABLE employee    ACL     <  GRANT SELECT,UPDATE ON TABLE public.employee TO "SuperVisor" WITH GRANT OPTION;
GRANT SELECT,INSERT ON TABLE public.employee TO "Staff" WITH GRANT OPTION;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.employee TO "Manager" WITH GRANT OPTION;
GRANT SELECT ON TABLE public.employee TO "Analyst" WITH GRANT OPTION;
          public          postgres    false    214            �            1259    18132    employee_id_seq    SEQUENCE     �   CREATE SEQUENCE public.employee_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.employee_id_seq;
       public          postgres    false    214            ~           0    0    employee_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.employee_id_seq OWNED BY public.employee.id;
          public          postgres    false    221            �            1259    18419    employee_id_seq1    SEQUENCE     �   ALTER TABLE public.employee ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.employee_id_seq1
    START WITH 28
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    214            �            1259    18133    establishment    TABLE     C  CREATE TABLE public.establishment (
    id integer NOT NULL,
    phone_number character varying(20),
    number_of_employees integer,
    specialty_name character varying(20),
    establishment_name character varying(20),
    address character varying(100),
    post character varying(10),
    adress2 public.newaddress
);
 !   DROP TABLE public.establishment;
       public         heap    postgres    false    894                       0    0    TABLE establishment    ACL     P  GRANT SELECT,UPDATE ON TABLE public.establishment TO "SuperVisor" WITH GRANT OPTION;
GRANT SELECT,INSERT ON TABLE public.establishment TO "Staff" WITH GRANT OPTION;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.establishment TO "Manager" WITH GRANT OPTION;
GRANT SELECT ON TABLE public.establishment TO "Analyst" WITH GRANT OPTION;
          public          postgres    false    222            �            1259    18138    establishment_employee    TABLE     x   CREATE TABLE public.establishment_employee (
    establishment_id integer NOT NULL,
    employee_id integer NOT NULL
);
 *   DROP TABLE public.establishment_employee;
       public         heap    postgres    false            �           0    0    TABLE establishment_employee    ACL     t  GRANT SELECT,UPDATE ON TABLE public.establishment_employee TO "SuperVisor" WITH GRANT OPTION;
GRANT SELECT,INSERT ON TABLE public.establishment_employee TO "Staff" WITH GRANT OPTION;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.establishment_employee TO "Manager" WITH GRANT OPTION;
GRANT SELECT ON TABLE public.establishment_employee TO "Analyst" WITH GRANT OPTION;
          public          postgres    false    223            �            1259    18141    establishment_id_seq    SEQUENCE     �   CREATE SEQUENCE public.establishment_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.establishment_id_seq;
       public          postgres    false    222            �           0    0    establishment_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.establishment_id_seq OWNED BY public.establishment.id;
          public          postgres    false    224            �            1259    18423    establishment_id_seq1    SEQUENCE     �   ALTER TABLE public.establishment ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.establishment_id_seq1
    START WITH 5
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    222            �            1259    18142    receipt    TABLE     �   CREATE TABLE public.receipt (
    id_receipt integer NOT NULL,
    client_id integer,
    establishment_id integer,
    price integer
);
    DROP TABLE public.receipt;
       public         heap    postgres    false            �           0    0    TABLE receipt    ACL     �   GRANT SELECT,INSERT ON TABLE public.receipt TO "Staff" WITH GRANT OPTION;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.receipt TO "Manager" WITH GRANT OPTION;
GRANT SELECT ON TABLE public.receipt TO "Analyst" WITH GRANT OPTION;
          public          postgres    false    225            �            1259    18402    receipt_booking    TABLE     j   CREATE TABLE public.receipt_booking (
    id_booking integer NOT NULL,
    id_receipt integer NOT NULL
);
 #   DROP TABLE public.receipt_booking;
       public         heap    postgres    false            �           0    0    TABLE receipt_booking    ACL       GRANT SELECT,INSERT ON TABLE public.receipt_booking TO "Staff" WITH GRANT OPTION;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.receipt_booking TO "Manager" WITH GRANT OPTION;
GRANT SELECT ON TABLE public.receipt_booking TO "Analyst" WITH GRANT OPTION;
          public          postgres    false    231            �            1259    18408    receipt_id_receipt_seq    SEQUENCE     �   ALTER TABLE public.receipt ALTER COLUMN id_receipt ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.receipt_id_receipt_seq
    START WITH 7
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    225            �            1259    18157    receipt_id_seq    SEQUENCE     �   CREATE SEQUENCE public.receipt_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.receipt_id_seq;
       public          postgres    false    225            �           0    0    receipt_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.receipt_id_seq OWNED BY public.receipt.id_receipt;
          public          postgres    false    227            �            1259    18149    service    TABLE     m   CREATE TABLE public.service (
    id integer NOT NULL,
    name character varying(255),
    price integer
);
    DROP TABLE public.service;
       public         heap    postgres    false            �           0    0    TABLE service    ACL     �   GRANT SELECT,INSERT ON TABLE public.service TO "Staff" WITH GRANT OPTION;
GRANT SELECT ON TABLE public.service TO "Analyst" WITH GRANT OPTION;
          public          postgres    false    226            �            1259    18158    service_id_seq    SEQUENCE     �   CREATE SEQUENCE public.service_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.service_id_seq;
       public          postgres    false    226            �           0    0    service_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.service_id_seq OWNED BY public.service.id;
          public          postgres    false    228            �            1259    18168    users    TABLE     �   CREATE TABLE public.users (
    id integer NOT NULL,
    username character varying(255),
    password character varying(255),
    employee_id integer
);
    DROP TABLE public.users;
       public         heap    postgres    false            �           0    0    TABLE users    ACL     �   GRANT SELECT,UPDATE ON TABLE public.users TO "SuperVisor" WITH GRANT OPTION;
GRANT SELECT ON TABLE public.users TO "Analyst" WITH GRANT OPTION;
          public          postgres    false    229            �            1259    18173    users_id_seq    SEQUENCE     �   CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.users_id_seq;
       public          postgres    false    229            �           0    0    users_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;
          public          postgres    false    230            �            1259    18422    users_id_seq1    SEQUENCE     �   ALTER TABLE public.users ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.users_id_seq1
    START WITH 28
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    229            �           2604    18174    booking id_booking    DEFAULT     p   ALTER TABLE ONLY public.booking ALTER COLUMN id_booking SET DEFAULT nextval('public.booking_id_seq'::regclass);
 A   ALTER TABLE public.booking ALTER COLUMN id_booking DROP DEFAULT;
       public          postgres    false    216    215            �           2604    18180 
   service id    DEFAULT     h   ALTER TABLE ONLY public.service ALTER COLUMN id SET DEFAULT nextval('public.service_id_seq'::regclass);
 9   ALTER TABLE public.service ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    228    226            X          0    18117    booking 
   TABLE DATA           l   COPY public.booking (id_booking, client_id, service_id, booking_date, master_rating, master_id) FROM stdin;
    public          postgres    false    215   ��       Z          0    18122    client 
   TABLE DATA           >   COPY public.client (id, full_name, client_status) FROM stdin;
    public          postgres    false    217   �       [          0    18127    client_bonus 
   TABLE DATA           <   COPY public.client_bonus (id, client_id, bonus) FROM stdin;
    public          postgres    false    218   �       W          0    18107    employee 
   TABLE DATA           �   COPY public.employee (id, full_name, "position", contact_information, experience, salary, brief_information, age, rating) FROM stdin;
    public          postgres    false    214   Ԝ       _          0    18133    establishment 
   TABLE DATA           �   COPY public.establishment (id, phone_number, number_of_employees, specialty_name, establishment_name, address, post, adress2) FROM stdin;
    public          postgres    false    222   ,�       `          0    18138    establishment_employee 
   TABLE DATA           O   COPY public.establishment_employee (establishment_id, employee_id) FROM stdin;
    public          postgres    false    223   ;�       b          0    18142    receipt 
   TABLE DATA           Q   COPY public.receipt (id_receipt, client_id, establishment_id, price) FROM stdin;
    public          postgres    false    225   ��       h          0    18402    receipt_booking 
   TABLE DATA           A   COPY public.receipt_booking (id_booking, id_receipt) FROM stdin;
    public          postgres    false    231   ̢       c          0    18149    service 
   TABLE DATA           2   COPY public.service (id, name, price) FROM stdin;
    public          postgres    false    226   �       f          0    18168    users 
   TABLE DATA           D   COPY public.users (id, username, password, employee_id) FROM stdin;
    public          postgres    false    229   ��       �           0    0    booking_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.booking_id_seq', 65, true);
          public          postgres    false    216            �           0    0    client_bonus_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.client_bonus_id_seq', 505, true);
          public          postgres    false    219            �           0    0    client_bonus_id_seq1    SEQUENCE SET     D   SELECT pg_catalog.setval('public.client_bonus_id_seq1', 506, true);
          public          postgres    false    234            �           0    0    client_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.client_id_seq', 504, true);
          public          postgres    false    220            �           0    0    client_id_seq1    SEQUENCE SET     >   SELECT pg_catalog.setval('public.client_id_seq1', 511, true);
          public          postgres    false    237            �           0    0    employee_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.employee_id_seq', 24, true);
          public          postgres    false    221            �           0    0    employee_id_seq1    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.employee_id_seq1', 28, true);
          public          postgres    false    233            �           0    0    establishment_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.establishment_id_seq', 4, true);
          public          postgres    false    224            �           0    0    establishment_id_seq1    SEQUENCE SET     C   SELECT pg_catalog.setval('public.establishment_id_seq1', 6, true);
          public          postgres    false    236            �           0    0    receipt_id_receipt_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.receipt_id_receipt_seq', 42, true);
          public          postgres    false    232            �           0    0    receipt_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.receipt_id_seq', 6, true);
          public          postgres    false    227            �           0    0    service_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.service_id_seq', 6, true);
          public          postgres    false    228            �           0    0    users_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.users_id_seq', 27, true);
          public          postgres    false    230            �           0    0    users_id_seq1    SEQUENCE SET     <   SELECT pg_catalog.setval('public.users_id_seq1', 28, true);
          public          postgres    false    235            �           2606    18183    booking booking_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.booking
    ADD CONSTRAINT booking_pkey PRIMARY KEY (id_booking);
 >   ALTER TABLE ONLY public.booking DROP CONSTRAINT booking_pkey;
       public            postgres    false    215            �           2606    18185    client_bonus client_bonus_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.client_bonus
    ADD CONSTRAINT client_bonus_pkey PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.client_bonus DROP CONSTRAINT client_bonus_pkey;
       public            postgres    false    218            �           2606    18187    client client_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.client DROP CONSTRAINT client_pkey;
       public            postgres    false    217            �           2606    18189    employee employee_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.employee
    ADD CONSTRAINT employee_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.employee DROP CONSTRAINT employee_pkey;
       public            postgres    false    214            �           2606    18191 2   establishment_employee establishment_employee_pkey 
   CONSTRAINT     �   ALTER TABLE ONLY public.establishment_employee
    ADD CONSTRAINT establishment_employee_pkey PRIMARY KEY (establishment_id, employee_id);
 \   ALTER TABLE ONLY public.establishment_employee DROP CONSTRAINT establishment_employee_pkey;
       public            postgres    false    223    223            �           2606    18193     establishment establishment_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.establishment
    ADD CONSTRAINT establishment_pkey PRIMARY KEY (id);
 J   ALTER TABLE ONLY public.establishment DROP CONSTRAINT establishment_pkey;
       public            postgres    false    222            �           2606    18406 $   receipt_booking receipt_booking_pkey 
   CONSTRAINT     v   ALTER TABLE ONLY public.receipt_booking
    ADD CONSTRAINT receipt_booking_pkey PRIMARY KEY (id_booking, id_receipt);
 N   ALTER TABLE ONLY public.receipt_booking DROP CONSTRAINT receipt_booking_pkey;
       public            postgres    false    231    231            �           2606    18195    receipt receipt_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.receipt
    ADD CONSTRAINT receipt_pkey PRIMARY KEY (id_receipt);
 >   ALTER TABLE ONLY public.receipt DROP CONSTRAINT receipt_pkey;
       public            postgres    false    225            �           2606    18197    service service_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.service
    ADD CONSTRAINT service_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.service DROP CONSTRAINT service_pkey;
       public            postgres    false    226            �           2606    18199    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    229            �           2620    18201    booking booking_rating_insert    TRIGGER     �   CREATE TRIGGER booking_rating_insert AFTER INSERT ON public.booking FOR EACH ROW EXECUTE FUNCTION public.update_employee_rating();
 6   DROP TRIGGER booking_rating_insert ON public.booking;
       public          postgres    false    215    252            �           2620    18202 #   receipt update_client_bonus_trigger    TRIGGER     �   CREATE TRIGGER update_client_bonus_trigger AFTER INSERT ON public.receipt FOR EACH ROW EXECUTE FUNCTION public.update_client_bonus();
 <   DROP TRIGGER update_client_bonus_trigger ON public.receipt;
       public          postgres    false    251    225            �           2620    18203 $   booking update_client_status_trigger    TRIGGER     �   CREATE TRIGGER update_client_status_trigger AFTER INSERT ON public.booking FOR EACH ROW EXECUTE FUNCTION public.update_client_status();
 =   DROP TRIGGER update_client_status_trigger ON public.booking;
       public          postgres    false    239    215            �           2606    18204    booking booking_client_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.booking
    ADD CONSTRAINT booking_client_id_fkey FOREIGN KEY (client_id) REFERENCES public.client(id);
 H   ALTER TABLE ONLY public.booking DROP CONSTRAINT booking_client_id_fkey;
       public          postgres    false    3239    215    217            �           2606    18209 (   client_bonus client_bonus_client_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.client_bonus
    ADD CONSTRAINT client_bonus_client_id_fkey FOREIGN KEY (client_id) REFERENCES public.client(id);
 R   ALTER TABLE ONLY public.client_bonus DROP CONSTRAINT client_bonus_client_id_fkey;
       public          postgres    false    218    3239    217            �           2606    18214 >   establishment_employee establishment_employee_employee_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.establishment_employee
    ADD CONSTRAINT establishment_employee_employee_id_fkey FOREIGN KEY (employee_id) REFERENCES public.employee(id);
 h   ALTER TABLE ONLY public.establishment_employee DROP CONSTRAINT establishment_employee_employee_id_fkey;
       public          postgres    false    3235    223    214            �           2606    18219 C   establishment_employee establishment_employee_establishment_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.establishment_employee
    ADD CONSTRAINT establishment_employee_establishment_id_fkey FOREIGN KEY (establishment_id) REFERENCES public.establishment(id);
 m   ALTER TABLE ONLY public.establishment_employee DROP CONSTRAINT establishment_employee_establishment_id_fkey;
       public          postgres    false    222    3243    223            �           2606    18409    receipt_booking id_1    FK CONSTRAINT     �   ALTER TABLE ONLY public.receipt_booking
    ADD CONSTRAINT id_1 FOREIGN KEY (id_booking) REFERENCES public.booking(id_booking) NOT VALID;
 >   ALTER TABLE ONLY public.receipt_booking DROP CONSTRAINT id_1;
       public          postgres    false    3237    215    231            �           2606    18414    receipt_booking id_2    FK CONSTRAINT     �   ALTER TABLE ONLY public.receipt_booking
    ADD CONSTRAINT id_2 FOREIGN KEY (id_receipt) REFERENCES public.receipt(id_receipt) NOT VALID;
 >   ALTER TABLE ONLY public.receipt_booking DROP CONSTRAINT id_2;
       public          postgres    false    231    3247    225            �           2606    18229    receipt receipt_client_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.receipt
    ADD CONSTRAINT receipt_client_id_fkey FOREIGN KEY (client_id) REFERENCES public.client(id);
 H   ALTER TABLE ONLY public.receipt DROP CONSTRAINT receipt_client_id_fkey;
       public          postgres    false    217    225    3239            �           2606    18234 %   receipt receipt_establishment_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.receipt
    ADD CONSTRAINT receipt_establishment_id_fkey FOREIGN KEY (establishment_id) REFERENCES public.establishment(id);
 O   ALTER TABLE ONLY public.receipt DROP CONSTRAINT receipt_establishment_id_fkey;
       public          postgres    false    222    225    3243            �           2606    18239    booking service_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.booking
    ADD CONSTRAINT service_id FOREIGN KEY (service_id) REFERENCES public.service(id) NOT VALID;
 <   ALTER TABLE ONLY public.booking DROP CONSTRAINT service_id;
       public          postgres    false    215    3249    226            �           2606    18244    users users_employee_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_employee_id_fkey FOREIGN KEY (employee_id) REFERENCES public.employee(id);
 F   ALTER TABLE ONLY public.users DROP CONSTRAINT users_employee_id_fkey;
       public          postgres    false    3235    229    214            V           3256    18401    employee edit_employee    POLICY     �  CREATE POLICY edit_employee ON public.employee TO "SuperVisor" USING ((id IN ( SELECT establishment_employee.employee_id
   FROM public.establishment_employee
  WHERE (establishment_employee.establishment_id = ( SELECT establishment_employee_1.establishment_id
           FROM (public.establishment_employee establishment_employee_1
             JOIN public.users USING (employee_id))
          WHERE ((users.username)::text = CURRENT_USER))))));
 .   DROP POLICY edit_employee ON public.employee;
       public          postgres    false    223    223    229    214    229    214            U           3256    18250    establishment edit_salon    POLICY       CREATE POLICY edit_salon ON public.establishment TO "SuperVisor" USING ((id = ( SELECT establishment_employee.establishment_id
   FROM (public.establishment_employee
     JOIN public.users USING (employee_id))
  WHERE ((users.username)::text = CURRENT_USER))));
 0   DROP POLICY edit_salon ON public.establishment;
       public          postgres    false    222    222    229    229    223    223            Q           0    18107    employee    ROW SECURITY     6   ALTER TABLE public.employee ENABLE ROW LEVEL SECURITY;          public          postgres    false    214            R           0    18133    establishment    ROW SECURITY     ;   ALTER TABLE public.establishment ENABLE ROW LEVEL SECURITY;          public          postgres    false    222            T           3256    18251 (   establishment look_establishment_analyst    POLICY     \   CREATE POLICY look_establishment_analyst ON public.establishment TO "Analyst" USING (true);
 @   DROP POLICY look_establishment_analyst ON public.establishment;
       public          postgres    false    222            S           3256    18400    employee view_employee_analyst    POLICY     R   CREATE POLICY view_employee_analyst ON public.employee TO "Analyst" USING (true);
 6   DROP POLICY view_employee_analyst ON public.employee;
       public          postgres    false    214            X   >   x�m˱� �X��_zq�u؟9 ޻%�DB!�8-��#���E��(�&����E��I��t�      Z     x��]Ɏ$�=�|E��A�O�M'�|��]��3�,0^$H��lߺ{Ԟ���_��#3��U�$3�B��JV2�^��e5ۍ?�����h��]��_܌�����p}5~g�|k�|a�}���<���������w���+���k��7W�O����{��'����~q{�����d/{�L�ƿ�{���|�����j�v|<|��n��}��񭽈���.�������}������}�}�͙����݋����?��;i;��4|tyy/�A�ol��/{3�;
�� jG/��t7qqU������O{��~��t����썽X����l��O�xt���_�	]O�����6wk���^7���<�s�P�����LI���_��l�(!~t_����^���\����Ez6;�N�<|<�NS8�q���cn\�5�H��UAF5~��1�y�6�?����-B�9!$��xm��.��\��JHQL���>���S�T�W˚��օ���t�%�&*��z�}m�3,�D��������畏.�a�9G��������01L�?���b�)p��F?���J�3T0�t�s4<:�v¢�(����i|�"�_A�� 
<��4�'�]���初�R�r�|�#�^�筨"��׏��N�^S�ی�s��Fma>/�l9L��p	���-m>~}��|�E-$qA2�.w��g�u�/��NݞYt�D&H��?Zh���ν��\۳�9�����<�S�g/]�ofP-릤����{�58K݅>�v�Sk�����E��pDfm��p��e�9=dJNJ���8cSi��ة��s��τ�TV��b*�/�cw���P����?���?��gzwY�ܻ�6���wrm��.���œ�@�߻_ �K�P��s����r?}pY�+3ӻ��̋���{e���W��G�ey���tD	�y�R��� �_��7ı�s����
9E�����wӻ���3�D��[9��}p�<"�6�x��Z�)�����@�ŵ�Il�����m�ߓ��EA��6�弍*/Q��H�w�v"��7�s/�����Ժ�sW��r h*{�3�L]l���D�$�J��V�O``��XI�$kR�Iۻ��;�LvUv�$}���nWْ����y�������.-�u������a��t�kS��}�u��&�7v:�e�>n����CI6#ԉc�������vn���[I����m�Ȃ��#u�ׇ�#y���f��o][C�T������ײV��#<�!h��"h:D��^w�`lV�d�'$7�I�R.;����&D~m#�tF���筃�Lw��3zn�G����0b�S�Rr�a�;�����߼��	Ω��F:�@o��D��is�|�a��e���1�=����+-�{����_����m����>�~2?�~bMa������XxR�����@W����d��s\bȓ���Z��S~�����n����!hB��d0��R����򀶝��pʱ� N[�3)�q;���u��[�%��d�:��N�C�<��>�0��H���2������+�紅���"SS8H;��Yi62��	z@��Q�-�
���iRYk �7{r�)
���4�"ɇ�H{�T��R����'���'Ӟ"�ݱw9v����W�c��N�wT 6�{,7��*�O�������fy�h�A�:JԹU��E�d�R5���A����j@ѠV�Nr5��ߤW���5��ߤX)�Ւ5L��f�5��@��6�� D�n� ���L ��5�$]����5ȡA���k���|$�wү�� �6�)�Y�r4J�@�V[�.��9�R� �T�@6�f�	�A� ś% �B� %E 1���J5��t����4@rP��:I�Y���&�A� �� }�Z� -;��UA��h�6����I� M��DT�j�	ύ���`O]���v]c�Zt��t��t�q��XU6��Nh���HF�>�1���s^��=|�q��l7���(,��Yy4��>�z�(;�;)���E۔w>����B��~�h0���"�\�HZ�}�ܜ	��i2҉<]�
qYY�[]�ĜR�~{3�x�Vd�٢�S����E�T0�]6z�٪�ܵdC3�H���'�ˬ��0ԝW7��lv��I�-C�����ۤy�u��e��ꪲ>&�hQi���Ϫq�Ԣ�U2�娗�+�c�� ?s�ę����`F<��V'����Zcҏ�:[�G��H=Z��Ź_v$�*�e�<�i^ˈ��b��X�~p[C/�0\*#n��� �2�bu�ڻ������jI�AF]GU84?8zQwEu�:���.<#��jO��	��Gp
Y��$���N7�`A�UT��[l�W��[�c��!V�rYH&b�Z~JÄ
j����tD���J��������~l¿>|�ƋGΌHgZT^1�´����D�����)��+�)[�N�eյ�%�K�frmL��SCd?W�
�����N�U�`g�Y�#���GU��x��g(Yѐ^�e?����k/�^t��.�4#"&�3�h�Z=�*.�Ck����}�f�����<rjYM��S�Ez�~gZ�[����dc�����KFT��ˌ��u�ˉ*�L�=]~dD�v�fXή.kN|M#(�	�p��=�݀ F�W��?K��9�?d]�������(-R�V�0s�i�ԩE-��#|`땘�,R��:��9�#'��A5��D�UbFD���h������d<L�j��{C�s�KZ�V�c�3�e���oq`�G�a6�Vg�̨���QY��O�(ȸI�J�aFLL3rR��ِ3�ʆ��\S+r柺Vŗa3r&�����S���ʆ�'�*ِԬ>58=�ʆ<t�TĄ�zW��=�+���+�ĳ<,�����a)fC�X
ِ�N�*d�u?K2��e�`C�yY�*C'KA�a��R�Ά�sճ!ƞ�o�{}-u9gz����P�.��u[��l��å���s)�~�]*kP�\/�	/51��l(|�Kʘ� �d�z�bC��]PA���b6���e�{�B&�{_��I��
6�1LU%��S�q*�����v6$���ˆ*t�T��?��.���զ�`F�#���@~��b��_�N��ԡ#�"":t�����P��a*�P'�1�x���*6�럂\�
�����	�1�lhBoL2�S�Lf�!�U�c*+ф���3�;�.&��M�7���7�>�q=259���/�W��q�9d��	��-S��8���r����̔`?�O��q�25	=31���|�L"�\3�؄�͔�!B���GH8i2b:h��a�;S�>=\4�.��������Y�)�8��IǄ��h���h��u� ��A��E���E����y�v��^.�=\4�{�h�׻h��t� os� ��A��Alu� ��h��h{�h�]4��\4�-.�>.�v���.}\4(z�hP��hP�qѠ��A��A��A��E����e�zʞ.�m.�}\4(��hP��hP��hP�qѠ��A��A��E���E����U��U��U_�>.T=\4��]4���hP�pѠnwѠnqѠ��A��E����u��uO�6�>.4�\4hZ]4hZ]4h��h��pѠiwѠ��A��C��E-�1�u�����E�����o����l�/o���^���,>R�����G�Wǿ�zOW&����~��ٳ�U3��      [   �
  x�5�ۑ)����B��c��o�f��Fp��[P�H�j��x��؍4��=N#���m�q3z�F���k��v����5��Fcf������l�q��H#ew3->uid�>o#��g_#�o|=�F�=G#�K2��O�l�/{f#�G��Ҟ�����yzʷ1%o#��z�F���}�d�5i��+ڲS�g#Ō��-��Qr5RҸ��r�h�:���+����z�����k$�6�mۡů�H��:���;)?^�F����H����4�9���H�E_#��u�k�.���h$ڼ��FZ�?���f�#g���g5Ҡ�g7�J�h��n>zi�!�����_#�������7ڵK|��F1�f#�5n����yw#K�F�o�{i~�^#�����5�����;��`�h0E7�5�K���dW#��f���ʞ����cL}J�^{��B�m~-$櫕���n%��9s��� �Et�<&ժOaT��[�E���aW-�9>=�����c�������pI�˖:c�Ҟ���������Ӹ:�9fZ�f0��4�m���c�0J�t���,�8�ӵG����<D�:v jԙ>_8�lG�c�)�qݢdv�Q{��bbOȨ�FbF�*g�5�5�[����R8�Fݣ��϶Kt�-C�m��iws��tڝ~�xJ����ɤZ]�a��2�?��keQg�\q��IυZVX�;��[��م�\��{�E>k�Y�jWQ�-h,���Li+����w�c L�AuɄΌ]`w��p��_񻖛���d�vsF7��:� S?9l�J��C�׶� �
Z|�p�s�W��&�YG[�	W�.6wm<�f���D������? ӸV�$V������y1�Vr�1~i� ��}_�L�>0~���[��� ]��f�h���;{�D�S�qR��_x�_J��m�-�=��3����>���a	feX*�+,Q�`Ή!>��⒟��#�e!��j~Y?�:�tR��y|�]�X$)ڮQ�f�n�ڦ�v�f����\�����x`\��0P����HGk0v�T�I���$U�Hյ<U ��C�E](ER  � ����N���r[�|�`*TyG2�I٬h.�]it�YQ��3��D8Q�t&�j���"�y9�A�p��YCȡ-�ir����<5�Q�b��cwa�b0X=��Q.�uL�#.N�.I�CE��3�h�Z��c�W�F�� ��5�� �Gw'@h�uB����S�#0��d��@ QU�Y@�;,���NU:�<���-�%'J��	<C�N�~@.��b
,��	���mQZU4��c�*N=]��󦱭��汋�������K�U�|�*8Eӷ�9~lX���h0T/�*ڮ�bs�������y�0���PY��@Tm�04��MD@������H@Q5�*:h�:� ���u��ϡ
 �.�qDA���*�zDK�4��;�;����nqLn!9tQ�A
�e��D ��������)f�n 7�� ئR>�6U�\��(�9=�MM��3,b�q��g\��ʖ`L�]=��6tX���8��|���q&hS/y�#�ō�8a��Z<��	��$�hǄn�ڎaT��ɪ��m��ȔM�6�q�bX�q+�0?V�|�7E}��.��1_΢/�^�U��J�z�7��b^��g��� X�r[��\F�>��z�����MU�5�ņ�^/�.�Xo�xt�w���TʱĦ!i��oVPZ��K�\�ƶ�q�Qi�����F���*g��TG�n��E\��Ϙ�M�L��u�2$��L'pS���8Z���S��-pS8����r](�M��.���B1��h`�fA�#�,dHn׏/���o�·Ya���(�k����oj��&�S�cq8��|��8�=�:v��_Lkm/8ծyl�rmԘי���~��M=�@7�n�6tSw�%�F�pSbO�m'pS׍z��p��XѦ�����+��m��D�lS��T�u��6�l���ڰM��)�4_`�V�����/Cʏ�׉s^��������e�ӏ�ߙ1�[I~�6\����[XG����7��q�8<k��)�����:�R7E?���_低���r���"{9?oJV�pBV����-+$���)(L�߼����9�	���1=����`ؼL��B�]�}�5昀lYn5H"�oY�� m�#�o�6c�K���-��o����aCX
�ʞ���h�n�05�48<Ϥ�1���ߺ��̇�w�mY.��6�=]�6��2ރ���)7fN�3�v��`[)}q<��M���YL�Ł���M�M���Ҟ�oY�yl��-���3��΋���Kf�e�c�˸
������5��KOSڤ�&�6�t�5%�s�T0�����|���R�ZN�	P��&���CţV���X�~xCg4�.�i�+"��C�m
H`���/�ʇs��������R��R�׏#Dg�7u��˃��y�[��XN.)ż��u���Zs�:ئ�)�a\//J5Usp�Շ4�m�6.ئ��N�;>�T��	ܲ.��!tS���"�	o%׌ޛw{v��Zq�;��k�ΛN���e9p�l�0`p*��hSCf���O�*����)��mjV&l˺�>�+����Y����#�)'�D�u�|�t{��7��=�
���,��W����=�[� ��{�����o����w��E6������?���      W   H  x��W�n7|��b��_����	ڢEc'P�A}�S#�R+ie��?���W�"��ai�$g�I��Y��<��K���m�v�2.���b�}�`�>NY:�_i_��&��~Ƅ%{��
ǅa�	N��o�$��n� ����1��}�(��gJ2�<���+D�`l�^�_	R�c�;\��e?a|�u��"�x�5�+�c������1���)B�t��o��Q֙��A)嬶@�B3(SAi/1B[�a.p�a�$�������7@l{�d(p�a��0��|�(�Ԃ�|!R*_�;<�m�&�'���4�X�Z��i�R�eY����/���Eļ|(�]��'$-���Yz!Uv�_����R���]7��lP>��p�	e�@7��j�Cb;�L���qQ�PU��]��&������<�2l�A������f�=���{bI�����rP�	F�LDz�ݤ�9C�P��%�e1���8���k�B;)5p�K���>�gO/]|���*�4��ew�Aq��AqneG���dB�|�_�j��4�O���&��=S��y�7c��P�'(2x|�l�����.�^.��� �C�(
f�X��?`�a�B��H�L큆a=`s�S0iڳ ���#��Z�L��R@ (�i\��Zp%��Hn+�fNJ���jM|.Ϟ^a�%e�v�>��; #�v8<p8V�0/�y��i�/(��718��!ōvUrU�n_���Q���<�tw� �3x�FrT਒6<�j�%_@��xSX����$�뒜쾔��� ���[�^ 1B�>��rC�(�l+t����~��+WI�����L��:;FO�I�l ��v�E]��JxG�8��n���t�;##�Ɋ�R9�^/������m���g��]�5๸��>d�c�?5�^i�Ee����3�6��>�oD��	j�4��.��w�6z/X��V���p�{���Axͭ;���3�S�;-��s_:���e���rT��-e��:ԆJ�d1�5��r�Ī�1�(����K 3"	n����d�o;������+Žt�"��65��'���?�6)=      _   �   x���AN�0E��SX]�U�8�}	N�e��$���+;�)�4�\�ύ���G,�c�����8����9��7��8a ���-w|�O�o�sK�[���Q�)X�9Z<�,/=?X�mN�*�����d��P�0����<�o�E-zey��P
�i�PŨ��4�]�t�ē�Q|��h��{����[��4�[�4c�X���߰8��<x'�A���Z��N�%�Xz��Q]��$R��|���@e��U[1��X\����zG �      `   E   x�ͱ�0�Z�3HI�]����?�`q�b���PI�O�<N�Х6��ˤ4�4a�� �1/�. ?ol�      b   ,   x�31�42�4ⴴ4�2��42�M�L!��F�\1z\\\ �w      h      x�33�41����� 
��      c   �   x�m�A�0E��)8���x`�$��W�*E@���F��K]4M�^���Θ�� H�N
)�Bo�8mlp�J
�x�br>..�3Pݫ�.����=N�\�4�N��	�$W��$m��"��G��I)g�-�v%�tl�����[�#&�N$;%�~#Ck�7&#�~��2���6k��\�=      f   �
  x�m��n\�E��|����~KF 1b'@��TwWK^FR�_�ՌĤa[�d�L��]k��[����M�O�x~���<?���r;�������o���h�C�\]�ق�a�]H>�rq���}p5�1�fG�.��+�I��:�I��tUr�j˂5��B1�6W�>�5�TVm�o�mّO����������A>?����G�vWr�V��Sy��sF�ч���曔<rv�I�3fgq̨a�*Eb�Z�ȳ�2��m�дt	Q���^g����f5i�����t���>��>���w5J\�r�F2����9�A�=�ң�Z�ajT�Mj����M�%��Ti�)Xa�#�U)1�����x�Q�gY�����|�S<>������k?]���M�>O����:��k�j�beD�Msy���7�>��kexKz�ͥY�H��׾����r��P}w"Z��̱鈋?I�1��J������N��py����=����m3�TK���a�����k�W곪���Sݵ����c0�9G/�7��ٍd��l�[p~��y7�G����k�TKU+���*��/������]��m�<QK(�K�7�D�e�P�0�:��uj�,���9�%Fl~�ز8�ʥő
ϋ��nw�+OT�Z��h��2�)��C���S���~2{���}�����|u���F�	�v�d�ńM�O˷�])�6Am�'��OJ�nT���L쬈��&�l��@��٢ٴ��0W�,��2E]�a�p�����}�<P�ޮ��N}<���T��5���<�b饲9Iev�e�(�?i������A$+~Q�!D�"$�ꤴ� P[C�	��u�u���df��S������G�嵧v��v�����Y��՚�[�<��41Z�Ӂ:n��}�f*b�Z_�^eP��07#��D̉��)IN$')e��Ss@N��G�;Fy���N"tu�כ~�޴ty�û˫���.���!��q"(���;^fx�����<g�3�*��6ƫnf�9z��t�MA��<�9��������Լ��e*���y}�Ji��%g�%��)�KAs�+(kA�|Ɇ~gN��;�Pj�8�����p#k?m`>�|�E��R�f>��I����p��G9I����WT�k��{�o+]�p��=a��d�Giokf�p@����I�)�<�
07�P�Ϸ��[8�9gə+��4��'�0s���f�%j��T@�"�/�|��'{|yKyu52�A��`�C��tֹ$�����I��f�4Q�M�0-����y���@��P��[/(�k��/E1[Y��2�h:�|��|����˲���=�C��;�-w[�|cD�50�r�"����������� ���k�e�p����ll�5��\�cDv�!��Y^x�|�|��?_����������J]�mG���t��Azv�T�̐<Vh+��
	�@��3{Ǐ��|��ܬ�L�I7*��l2���?���#�P0�B���t��y>=�������s���*icZ'���:�� >��,�T��cA���n��1PGX�ןj�R>���옌�����Jە�kܫ���2��		'i�w����n���|=������ܕ�	��T\�%%��H:1�j���ѽһ��v2_9��#}sV���1{�9�+��v���mJ0ń�'��N���_���텹������o�
�#-�����[�]�W�J�c�k�?h�Y�v���M�Rs�ʓ�F$,��"!v��5X	Gl���Q�'pO$a����7�ޝ���wBu>���`Ba��j"mRAC��B�5#�}��D�	�.��V0z8&��yqsnd�~ A�dݷdaώ �-	ç|���x-�5�y%j%�/�/Dt�WҘI��LN<�=[Ex�\7�p�A`�փ�����|es8�v,Iă�y?GnL	�J�CN^�_��ӎ�����ݴ5:j4�e�V:$�������y��iy�5����Է���Nk!����4�at��'�I��s�$��邃��Sd�n��'�>lz^�"��ף����HW�l�"y�x81Qp>r��������_)=a�̤��%V��
G+k��l�϶H`���Cr/�w2L�{����ɇ����آWw:�\/@��fB�� xEc[x ����ʕ0�&��L@��o���@�q'����1�����A&,`ɮs��TQH��T���-�����O>���'�A���o�]�Z;'�ID�spq�.6�pi�'1��K[T��&zn=ղ��~X�s�c��dB��\*ļU��:�@0�Wt�� �S��H5{�Ϗz�t~��;�o��5C��L�Ქ$��X- R�Q�57816�>%��?C���D��������sF�5ֺ�@R`�	��>�|�j�w�{N���m�?���F%�Lo�lY;��}`�����c�� _䁙��pֽJ���;(rb����)�����󨅋9NLD�~L^.J��)�}���|���;R���zQ�����Np�?B�D�:�S�8��L�>�"a���:d���d�B�(4	�zNQn<���GD�/9�3�ơoI�/'_��������`!�π2� �лj����.�	���6G"n8A��{���9�W���`�Xd�M��bf݀.��f[����|s:����WX     