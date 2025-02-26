<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('enderecos', function (Blueprint $table) {
            $table->bigIncrements('id_endereco');
            $table->string('tp_endereco');
            $table->string('logradouro', 100);
            $table->string('numero', 10);
            $table->string('complemento',  100)->nullable();
            $table->string('bairro',  100);
            $table->string('cidade',  100);
            $table->string('estadoSigla', 100);
            $table->string('CEP',  9);
            $table->timestamp('dataCriacao')->useCurrent();

            $table->unsignedBigInteger('id_usuario'); // Chave estrangeira

            // Define a foreign key (equivalente ao @ManyToOne)
            $table->foreign('id_usuario')
                ->references('id_usuario')
                ->on('usuarios')
                ->onDelete('cascade'); // Ajuste conforme a necessidade

        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('enderecos');
    }
};
