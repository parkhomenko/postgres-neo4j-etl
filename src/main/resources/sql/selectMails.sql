select m.id as mail_id, m.mail_topic, m.mail_body, m.sent_date, m.delivery_date, m.status,
  us.id as sender_id, us.name as sender_name, us.age as sender_age, us.address as sender_address,
  ur.id as recipient_id, ur.name as recipient_name, ur.age as recipient_age, ur.address as recipient_address
from mails m
join users us on m.sender = us.id
join users ur on m.recipient = ur.id
limit ?
offset ?
