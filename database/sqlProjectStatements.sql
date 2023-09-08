select * from transfer_status;
select * from transfer_type;
select * from transfer;

SELECT t.transfer_id, tt.transfer_type_desc, ts.transfer_status_desc, t.amount, uf.username AS account_from_username, ut.username AS account_to_username
FROM transfer AS t
JOIN transfer_type AS tt ON t.transfer_type_id = tt.transfer_type_id
JOIN transfer_status AS ts ON t.transfer_status_id = ts.transfer_status_id
JOIN account AS af ON t.account_from = af.account_id
JOIN account AS at ON t.account_to = at.account_id
JOIN tenmo_user AS uf ON af.user_id = uf.user_id
JOIN tenmo_user AS ut ON at.user_id = ut.user_id
WHERE uf.user_id = 1003
ORDER BY t.transfer_id DESC;
//

SELECT t.transfer_id, tu.tenmo_user AS user_from, tu.tenmo_user AS user_to, ts.transfer_status_desc, tt.transfer_type_desc, t.amount
FROM tenmo_user AS tu
JOIN account AS a ON a.user_id = tu.user_id
JOIN transfer AS t ON t.account_to = a.account_to
JOIN transfer_type AS tt ON tt.transfer_type_id = t.transfer_type_id
JOIN transfer_status AS ts ON ts.transfer_status_id = t.transfer_status_id
WHERE tu.tenmo_user = (SELECT tenmo_id FROM tenmo_user );


//
SELECT t.transfer_id, t.transfer_type_id, t.transfer_status_id, t.account_from, t.account_to, t.amount
FROM transfer AS t
INNER JOIN account AS a ON a.account_id = t.account_from OR a.account_id = t.account_to
WHERE a.user_id = 1003;

SELECT t.transfer_id, tt.transfer_type_desc, ts.transfer_status_desc, t.amount,
    uf.username AS user_from, uf.username AS user_to
FROM transfer AS t
JOIN account AS af ON af.account_id = t.account_from
JOIN tenmo_user AS uf ON uf.user_id = af.user_id
JOIN transfer_type AS tt ON tt.transfer_type_id = t.transfer_type_id
JOIN transfer_status AS ts ON ts.transfer_status_id = t.transfer_status_id
WHERE /*user from */af.user_id = 1003;

select * from transfer;
//JOIN tenmo_user AS ut ON ut.user_id = at.user_id

//JOIN account AS at ON at.account_id = t.account_to
SELECT t.transfer_id, tu.tenmo_user AS user_from, tu.tenmo_user AS user_to, ts.transfer_status_desc, tt.transfer_type_desc, t.amount






SELECT t.transfer_id, tu.username AS user_from, tu.username AS user_to, ts.transfer_status_desc, tt.transfer_type_desc, t.amount
FROM tenmo_user AS tu
JOIN account AS a ON a.user_id = tu.user_id
JOIN transfer AS t ON t.account_to = a.account_id
JOIN transfer_type AS tt ON tt.transfer_type_id = t.transfer_type_id
JOIN transfer_status AS ts ON ts.transfer_status_id = t.transfer_status_id
WHERE tu.user_id = 1003;

SELECT tu.username FROM tenmo_user 
WHERE 

SELECT 
//works
SELECT t.transfer_id, tt.transfer_type_desc, ts.transfer_status_desc, t.amount,
    uf.username AS user_from, ut.username AS user_to
FROM transfer AS t
INNER JOIN account AS af ON af.account_id = t.account_from
INNER JOIN account AS at ON at.account_id = t.account_to
INNER JOIN tenmo_user AS uf ON uf.user_id = af.user_id
INNER JOIN tenmo_user AS ut ON ut.user_id = at.user_id
INNER JOIN transfer_type AS tt ON tt.transfer_type_id = t.transfer_type_id
INNER JOIN transfer_status AS ts ON ts.transfer_status_id = t.transfer_status_id
WHERE af.user_id = 1003 OR at.user_id = 1003
ORDER BY t.transfer_id;

SELECT *
FROM transfer AS t
JOIN account AS af ON af.account_id = t.account_from
JOIN account AS at ON at.account_id = t.account_to
JOIN tenmo_user AS uf ON uf.user_id = af.user_id
JOIN tenmo_user AS ut ON ut.user_id = at.user_id
WHERE af.user_id = 1002



JOIN transfer_type AS tt ON tt.transfer_type_id = t.transfer_type_id
INNER JOIN transfer_status AS ts ON ts.transfer_status_id = t.transfer_status_id
WHERE af.user_id = 1002 OR at.user_id = 1002;

SELECT * from transfer_status;
select * from transfer_type;



SELECT t.transfer_id, tt.transfer_type_desc, ts.transfer_status_desc, t.amount,
    uf.username AS user_from, ut.username AS user_to
FROM transfer AS t
INNER JOIN account AS af ON af.account_id = t.account_from
INNER JOIN account AS at ON at.account_id = t.account_to
INNER JOIN tenmo_user AS uf ON uf.user_id = af.user_id
INNER JOIN tenmo_user AS ut ON ut.user_id = at.user_id
INNER JOIN transfer_type AS tt ON tt.transfer_type_id = t.transfer_type_id
INNER JOIN transfer_status AS ts ON ts.transfer_status_id = t.transfer_status_id
WHERE af.user_id = 1004 AND t.transfer_status_id = 1 AND t.transfer_status_id = 2
ORDER BY t.transfer_id;

select* from tenmo_user;

start transaction;
Update transfer
set transfer_status_id = 3
where transfer_id = 3007;
rollback
select * from transfer;


SELECT transfer_status_id FROM transfer Where transfer_id = 3007;






