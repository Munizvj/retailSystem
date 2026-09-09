CREATE TABLE sales(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    total NUMERIC(19, 2) NOT NULL DEFAULT 0.00,
    payment_method VARCHAR(50) NOT NULL,
    sale_status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    finalize_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE item_sales(
    id BIGSERIAL PRIMARY KEY,
    sale_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    unit_price NUMERIC(19, 2) NOT NULL,
    sub_total NUMERIC(19, 2) NOT NULL,

    CONSTRAINT fk_item_sales_sale
        FOREIGN KEY(sale_id)
        REFERENCES sales (id)
        ON DELETE CASCADE,

    CONSTRAINT fk_item_sales_product
        FOREIGN KEY (product_id)
        REFERENCES products (id)
);

CREATE INDEX idx_item_sales_sale_id ON item_sales(sale_id);
CREATE INDEX idx_item_sales_product_id ON item_sales(product_id);
CREATE INDEX idx_sales_user_id ON sales(user_id);